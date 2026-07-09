package com.nexifotech.hotelsaas.feature.reports.domain.model

data class ReportsDashboardMetrics(
    val todaysRevenue: Double,
    val occupancyRate: Double,
    val totalBookings: Int,
    val totalGuests: Int
)
