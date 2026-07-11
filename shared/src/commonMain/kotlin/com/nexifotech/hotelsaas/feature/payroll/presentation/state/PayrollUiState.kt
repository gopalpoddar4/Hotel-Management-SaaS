package com.nexifotech.hotelsaas.feature.payroll.presentation.state

import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollRecord
import com.nexifotech.hotelsaas.feature.payroll.domain.model.PayrollSummary

data class PayrollUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val summary: PayrollSummary? = null,
    val allRecords: List<PayrollRecord> = emptyList(),
    val filteredRecords: List<PayrollRecord> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String = "All"
)

data class PayrollDetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val record: PayrollRecord? = null,
    val isMarkingPaid: Boolean = false,
    val isGeneratingPayslip: Boolean = false
)

data class PayslipUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val record: PayrollRecord? = null
)
