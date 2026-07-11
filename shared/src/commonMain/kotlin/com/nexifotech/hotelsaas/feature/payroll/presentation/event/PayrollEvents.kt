package com.nexifotech.hotelsaas.feature.payroll.presentation.event

sealed interface PayrollEvent {
    data class OnSearchQueryChanged(val query: String) : PayrollEvent
    data class OnFilterSelected(val filter: String) : PayrollEvent
    data object OnRefresh : PayrollEvent
}

sealed interface PayrollDetailsEvent {
    data object OnRefresh : PayrollDetailsEvent
    data object OnMarkAsPaid : PayrollDetailsEvent
    data object OnGeneratePayslip : PayrollDetailsEvent
}

sealed interface PayslipEvent {
    data object OnRefresh : PayslipEvent
    data object OnDownloadPdf : PayslipEvent
    data object OnPrint : PayslipEvent
    data object OnShare : PayslipEvent
}
