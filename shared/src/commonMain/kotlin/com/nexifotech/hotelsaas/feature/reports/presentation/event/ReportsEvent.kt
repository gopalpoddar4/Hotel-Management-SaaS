package com.nexifotech.hotelsaas.feature.reports.presentation.event

sealed interface ReportsEvent {
    data object LoadReports : ReportsEvent
    data class SearchChanged(val query: String) : ReportsEvent
    data class FilterChanged(val category: String) : ReportsEvent
    data class DateFilterChanged(val dateFilter: String) : ReportsEvent
    data object Refresh : ReportsEvent
}
