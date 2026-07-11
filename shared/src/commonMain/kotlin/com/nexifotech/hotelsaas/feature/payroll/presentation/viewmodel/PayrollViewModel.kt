package com.nexifotech.hotelsaas.feature.payroll.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.payroll.data.datasource.FakePayrollDataSource
import com.nexifotech.hotelsaas.feature.payroll.data.repository.PayrollRepositoryImpl
import com.nexifotech.hotelsaas.feature.payroll.domain.repository.PayrollRepository
import com.nexifotech.hotelsaas.feature.payroll.presentation.event.PayrollEvent
import com.nexifotech.hotelsaas.feature.payroll.presentation.state.PayrollUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PayrollViewModel(
    private val repository: PayrollRepository = PayrollRepositoryImpl(FakePayrollDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(PayrollUiState())
    val uiState: StateFlow<PayrollUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: PayrollEvent) {
        when (event) {
            is PayrollEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                performSearch()
            }
            is PayrollEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                performSearch()
            }
            PayrollEvent.OnRefresh -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val summary = repository.getPayrollSummary()
                val records = repository.getPayrollList()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        summary = summary,
                        allRecords = records,
                        filteredRecords = records
                    )
                }
                // Re-apply any existing search/filter
                performSearch()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }

    private fun performSearch() {
        viewModelScope.launch {
            val currentState = _uiState.value
            try {
                val filtered = repository.searchAndFilterPayroll(
                    currentState.searchQuery, 
                    currentState.selectedFilter
                )
                _uiState.update { it.copy(filteredRecords = filtered) }
            } catch (e: Exception) {
                // Ignore search error
            }
        }
    }
}
