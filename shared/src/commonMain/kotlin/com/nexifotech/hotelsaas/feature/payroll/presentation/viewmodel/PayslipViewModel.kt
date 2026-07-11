package com.nexifotech.hotelsaas.feature.payroll.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.payroll.data.datasource.FakePayrollDataSource
import com.nexifotech.hotelsaas.feature.payroll.data.repository.PayrollRepositoryImpl
import com.nexifotech.hotelsaas.feature.payroll.domain.repository.PayrollRepository
import com.nexifotech.hotelsaas.feature.payroll.presentation.event.PayslipEvent
import com.nexifotech.hotelsaas.feature.payroll.presentation.state.PayslipUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PayslipViewModel(
    private val payrollId: String,
    private val repository: PayrollRepository = PayrollRepositoryImpl(FakePayrollDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(PayslipUiState())
    val uiState: StateFlow<PayslipUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: PayslipEvent) {
        when (event) {
            PayslipEvent.OnRefresh -> {
                loadData()
            }
            PayslipEvent.OnDownloadPdf -> {
                // Dummy action handled by UI or effect
            }
            PayslipEvent.OnPrint -> {
                // Dummy action
            }
            PayslipEvent.OnShare -> {
                // Dummy action
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
}
