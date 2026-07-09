package com.nexifotech.hotelsaas.feature.reports.domain.usecase

import com.nexifotech.hotelsaas.feature.reports.domain.model.Report
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportDetailsData
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportsDashboardMetrics
import com.nexifotech.hotelsaas.feature.reports.domain.repository.ReportsRepository

class GetReportsDashboardUseCase(private val repository: ReportsRepository) {
    suspend operator fun invoke(): Result<ReportsDashboardMetrics> {
        return repository.getDashboardMetrics()
    }
}

class GetReportsUseCase(private val repository: ReportsRepository) {
    suspend operator fun invoke(query: String = "", category: String? = null, dateFilter: String? = null): Result<List<Report>> {
        return repository.getReports(query, category, dateFilter)
    }
}

class GetReportDetailsUseCase(private val repository: ReportsRepository) {
    suspend operator fun invoke(reportId: String): Result<ReportDetailsData> {
        return repository.getReportDetails(reportId)
    }
}

data class ReportsUseCases(
    val getDashboardMetrics: GetReportsDashboardUseCase,
    val getReports: GetReportsUseCase,
    val getReportDetails: GetReportDetailsUseCase
)
