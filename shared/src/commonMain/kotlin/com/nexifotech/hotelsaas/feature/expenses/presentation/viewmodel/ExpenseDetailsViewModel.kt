package com.nexifotech.hotelsaas.feature.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.expenses.data.repository.DummyExpenseRepository
import com.nexifotech.hotelsaas.feature.expenses.domain.repository.ExpenseRepository
import com.nexifotech.hotelsaas.feature.expenses.domain.usecase.GetExpenseByIdUseCase
import com.nexifotech.hotelsaas.feature.expenses.presentation.event.ExpenseDetailsEvent
import com.nexifotech.hotelsaas.feature.expenses.presentation.state.ExpenseDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseDetailsViewModel(
    private val repository: ExpenseRepository = DummyExpenseRepository()
) : ViewModel() {

    private val getExpenseByIdUseCase = GetExpenseByIdUseCase(repository)

    private val _state = MutableStateFlow(ExpenseDetailsState())
    val state: StateFlow<ExpenseDetailsState> = _state.asStateFlow()

    fun onEvent(event: ExpenseDetailsEvent) {
        when (event) {
            is ExpenseDetailsEvent.LoadExpense -> {
                loadExpense(event.id)
            }
            is ExpenseDetailsEvent.ApproveExpense -> {
                viewModelScope.launch {
                    repository.approveExpense(event.id)
                    loadExpense(event.id)
                }
            }
            is ExpenseDetailsEvent.RejectExpense -> {
                viewModelScope.launch {
                    repository.rejectExpense(event.id)
                    loadExpense(event.id)
                }
            }
            is ExpenseDetailsEvent.MarkAsPaid -> {
                viewModelScope.launch {
                    repository.markAsPaid(event.id)
                    loadExpense(event.id)
                }
            }
            is ExpenseDetailsEvent.NavigateBack -> {
                // Handled in UI layer
            }
        }
    }

    private fun loadExpense(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val expense = getExpenseByIdUseCase(id)
                if (expense != null) {
                    _state.update { it.copy(isLoading = false, expense = expense) }
                } else {
                    _state.update { it.copy(isLoading = false, error = "Expense not found") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed to load expense details") }
            }
        }
    }
}
