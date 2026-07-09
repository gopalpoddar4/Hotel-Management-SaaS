package com.nexifotech.hotelsaas.feature.reports.domain.model

data class ReportDetailsData(
    val report: Report,
    val statistics: ReportStatistics,
    val kpis: List<ReportKpi>,
    val insights: List<ReportInsight>
)

data class ReportStatistics(
    val revenue: Double? = null,
    val bookings: Int? = null,
    val guests: Int? = null,
    val occupancyRate: Double? = null,
    val restaurantSales: Double? = null,
    val housekeepingPerformance: Double? = null
)

data class ReportKpi(
    val title: String,
    val value: String,
    val growth: String,
    val isPositive: Boolean
)

data class ReportInsight(
    val title: String,
    val description: String,
    val type: InsightType
)

enum class InsightType {
    HIGHLIGHT, NOTE, RECOMMENDATION
}
