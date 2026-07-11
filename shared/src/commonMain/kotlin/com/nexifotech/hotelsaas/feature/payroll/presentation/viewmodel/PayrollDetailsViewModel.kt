package com.nexifotech.hotelsaas.feature.payroll.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.payroll.data.datasource.FakePayrollDataSource
import com.nexifotech.hotelsaas.feature.payroll.data.repository.PayrollRepositoryImpl
import com.nexifotech.hotelsaas.feature.payroll.domain.repository.PayrollRepository
import com.nexifotech.hotelsaas.feature.payroll.presentation.event.PayrollDetailsEvent
import com.nexifotech.hotelsaas.feature.payroll.presentation.state.PayrollDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PayrollDetailsViewModel(
    private val payrollId: String,
    private val repository: PayrollRepository = PayrollRepositoryImpl(FakePayrollDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(PayrollDetailsUiState())
    val uiState: StateFlow<PayrollDetailsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: PayrollDetailsEvent) {
        when (event) {
            PayrollDetailsEvent.OnRefresh -> {
                loadData()
            }
            PayrollDetailsEvent.OnMarkAsPaid -> {
                markAsPaid()
            }
            PayrollDetailsEvent.OnGeneratePayslip -> {
                generatePayslip()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val record = repository.getPayrollDetails(payrollId)
                if (record != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            record = record
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Payroll record not found"
                        )
                    }
                }
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

    private fun markAsPaid() {
        viewModelScope.launch {
            _uiState.update { it.copy(isMarkingPaid = true) }
            try {
                repository.markSalaryPaid(payrollId)
                _uiState.update { it.copy(isMarkingPaid = false) }
                loadData() // Reload to reflect status changes
            } catch (e: Exception) {
                _uiState.update { it.copy(isMarkingPaid = false, error = "Failed to mark as paid") }
            }
        }
    }

    private fun generatePayslip() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingPayslip = true) }
            try {
                val success = repository.generatePayslip(payrollId)
                _uiState.update { it.copy(isGeneratingPayslip = false) }
                if (success) {
                    loadData()
                } else {
                    _uiState.update { it.copy(error = "Failed to generate payslip") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isGeneratingPayslip = false, error = "Failed to generate payslip") }
            }
        }
    }
}
