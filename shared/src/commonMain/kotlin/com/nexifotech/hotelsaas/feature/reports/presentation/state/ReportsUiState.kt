package com.nexifotech.hotelsaas.feature.reports.presentation.state

import com.nexifotech.hotelsaas.feature.reports.domain.model.Report
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportsDashboardMetrics

data class ReportsUiState(
    val isLoading: Boolean = false,
    val metrics: ReportsDashboardMetrics? = null,
    val reports: List<Report> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedDateFilter: String = "All Time",
    val error: String? = null
)
