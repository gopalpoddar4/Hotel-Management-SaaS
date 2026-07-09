package com.nexifotech.hotelsaas.feature.reports.data.repository

import com.nexifotech.hotelsaas.feature.reports.data.datasource.FakeReportsDataSource
import com.nexifotech.hotelsaas.feature.reports.domain.model.Report
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportDetailsData
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportsDashboardMetrics
import com.nexifotech.hotelsaas.feature.reports.domain.repository.ReportsRepository

class ReportsRepositoryImpl(
    private val dataSource: FakeReportsDataSource
) : ReportsRepository {
    
    override suspend fun getDashboardMetrics(): Result<ReportsDashboardMetrics> {
        return dataSource.getDashboardMetrics()
    }

    override suspend fun getReports(
        query: String,
        category: String?,
        dateFilter: String?
    ): Result<List<Report>> {
        return dataSource.getReports(query, category, dateFilter)
    }

    override suspend fun getReportDetails(reportId: String): Result<ReportDetailsData> {
        return dataSource.getReportDetails(reportId)
    }
}
