package com.nexifotech.hotelsaas.feature.reports.presentation.event

sealed interface ReportDetailsEvent {
    data class LoadReport(val reportId: String) : ReportDetailsEvent
    data object ExportPdf : ReportDetailsEvent
    data object ExportExcel : ReportDetailsEvent
    data object Share : ReportDetailsEvent
    data object Refresh : ReportDetailsEvent
}
