package com.nexifotech.hotelsaas.feature.reports.domain.model

data class Report(
    val id: String,
    val name: String,
    val category: String,
    val dateRange: String,
    val generatedTime: String,
    val generatedBy: String,
    val status: ReportStatus,
    val lastUpdated: String
)

enum class ReportStatus {
    GENERATED, PENDING, FAILED, SCHEDULED
}
