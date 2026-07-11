package com.nexifotech.hotelsaas.feature.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.expenses.data.repository.DummyExpenseRepository
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ExpenseCategory
import com.nexifotech.hotelsaas.feature.expenses.domain.repository.ExpenseRepository
import com.nexifotech.hotelsaas.feature.expenses.domain.usecase.GetExpensesUseCase
import com.nexifotech.hotelsaas.feature.expenses.domain.usecase.GetExpenseSummaryUseCase
import com.nexifotech.hotelsaas.feature.expenses.presentation.event.ExpensesEvent
import com.nexifotech.hotelsaas.feature.expenses.presentation.state.ExpensesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpensesViewModel(
    private val repository: ExpenseRepository = DummyExpenseRepository()
) : ViewModel() {

    private val getExpensesUseCase = GetExpensesUseCase(repository)
    private val getExpenseSummaryUseCase = GetExpenseSummaryUseCase(repository)

    private val _state = MutableStateFlow(ExpensesState(isLoading = true))
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                // Collect summary
                launch {
                    getExpenseSummaryUseCase().collect { summary ->
                        _state.update { it.copy(summary = summary) }
                    }
                }
                
                // Collect expenses
                launch {
                    getExpensesUseCase().collect { expenses ->
                        _state.update { 
                            it.copy(
                                isLoading = false, 
                                expenses = applyFilters(expenses, it.searchQuery, it.selectedCategory) 
                            ) 
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed to load expenses") }
            }
        }
    }

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.CategoryFilterChanged -> {
                _state.update { it.copy(selectedCategory = event.category) }
                refreshList()
            }
            is ExpensesEvent.SearchQueryChanged -> {
                _state.update { it.copy(searchQuery = event.query) }
                refreshList()
            }
            is ExpensesEvent.Refresh -> {
                loadData()
            }
            is ExpensesEvent.ExpenseClicked -> {
                // Handle navigation in UI via callback, or side effect
            }
        }
    }

    private fun refreshList() {
        viewModelScope.launch {
            getExpensesUseCase().collect { allExpenses ->
                val filtered = applyFilters(allExpenses, state.value.searchQuery, state.value.selectedCategory)
                _state.update { it.copy(expenses = filtered) }
            }
        }
    }

    private fun applyFilters(
        expenses: List<com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense>, 
        query: String, 
        category: ExpenseCategory?
    ): List<com.nexifotech.hotelsaas.feature.expenses.domain.model.Expense> {
        return expenses.filter {
            val matchesCategory = category == null || it.category == category
            val matchesQuery = query.isBlank() || 
                it.vendor.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.id.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }
}
