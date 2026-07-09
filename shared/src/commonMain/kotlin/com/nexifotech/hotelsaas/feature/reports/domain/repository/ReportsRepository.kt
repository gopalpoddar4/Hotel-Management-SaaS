package com.nexifotech.hotelsaas.feature.reports.domain.repository

import com.nexifotech.hotelsaas.feature.reports.domain.model.Report
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportDetailsData
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportsDashboardMetrics

interface ReportsRepository {
    suspend fun getDashboardMetrics(): Result<ReportsDashboardMetrics>
    suspend fun getReports(query: String = "", category: String? = null, dateFilter: String? = null): Result<List<Report>>
    suspend fun getReportDetails(reportId: String): Result<ReportDetailsData>
}
