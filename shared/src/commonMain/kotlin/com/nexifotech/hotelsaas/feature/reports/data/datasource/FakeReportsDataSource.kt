package com.nexifotech.hotelsaas.feature.reports.data.datasource

import com.nexifotech.hotelsaas.feature.reports.domain.model.InsightType
import com.nexifotech.hotelsaas.feature.reports.domain.model.Report
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportDetailsData
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportInsight
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportKpi
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportStatistics
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportStatus
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportsDashboardMetrics
import kotlinx.coroutines.delay

class FakeReportsDataSource {

    suspend fun getDashboardMetrics(): Result<ReportsDashboardMetrics> {
        delay(800) // Simulate network delay
        return Result.success(
            ReportsDashboardMetrics(
                todaysRevenue = 15420.50,
                occupancyRate = 0.85,
                totalBookings = 142,
                totalGuests = 315
            )
        )
    }

    suspend fun getReports(query: String = "", category: String? = null, dateFilter: String? = null): Result<List<Report>> {
        delay(1000)
        var filteredList = dummyReports
        
        if (query.isNotBlank()) {
            filteredList = filteredList.filter { 
                it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true) 
            }
        }
        
        if (!category.isNullOrBlank() && category != "All") {
            filteredList = filteredList.filter { it.category == category }
        }
        
        return Result.success(filteredList)
    }

    suspend fun getReportDetails(reportId: String): Result<ReportDetailsData> {
        delay(1200)
        val report = dummyReports.find { it.id == reportId } 
            ?: return Result.failure(Exception("Report not found"))
            
        val details = ReportDetailsData(
            report = report,
            statistics = ReportStatistics(
                revenue = 125000.0,
                bookings = 450,
                guests = 890,
                occupancyRate = 0.78,
                restaurantSales = 35000.0,
                housekeepingPerformance = 0.92
            ),
            kpis = listOf(
                ReportKpi("Revenue Growth", "₹125k", "+15%", true),
                ReportKpi("Occupancy", "78%", "+5%", true),
                ReportKpi("Cancellations", "12", "-2%", true),
                ReportKpi("RevPAR", "₹4,200", "+8%", true)
            ),
            insights = listOf(
                ReportInsight("High Weekend Demand", "Expect a 20% surge in bookings this upcoming weekend due to the local festival.", InsightType.HIGHLIGHT),
                ReportInsight("Housekeeping Delay", "Average room cleaning time increased by 5 minutes. Consider adding temporary staff.", InsightType.NOTE),
                ReportInsight("Upsell Opportunity", "Guests in standard rooms are frequently ordering room service. Offer breakfast packages at check-in.", InsightType.RECOMMENDATION)
            )
        )
        
        return Result.success(details)
    }

    private val dummyReports = listOf(
        Report(
            id = "REP-001",
            name = "Monthly Revenue Report",
            category = "Revenue",
            dateRange = "Oct 1 - Oct 31, 2023",
            generatedTime = "10:30 AM",
            generatedBy = "System",
            status = ReportStatus.GENERATED,
            lastUpdated = "Nov 1, 2023"
        ),
        Report(
            id = "REP-002",
            name = "Occupancy Forecast",
            category = "Occupancy",
            dateRange = "Nov 1 - Nov 30, 2023",
            generatedTime = "08:00 AM",
            generatedBy = "Jane Manager",
            status = ReportStatus.GENERATED,
            lastUpdated = "Nov 1, 2023"
        ),
        Report(
            id = "REP-003",
            name = "Q3 Financial Summary",
            category = "Financial",
            dateRange = "Jul 1 - Sep 30, 2023",
            generatedTime = "09:15 AM",
            generatedBy = "Finance Dept",
            status = ReportStatus.PENDING,
            lastUpdated = "Oct 5, 2023"
        ),
        Report(
            id = "REP-004",
            name = "Weekly Housekeeping Stats",
            category = "Housekeeping",
            dateRange = "Oct 23 - Oct 29, 2023",
            generatedTime = "11:00 AM",
            generatedBy = "System",
            status = ReportStatus.FAILED,
            lastUpdated = "Oct 30, 2023"
        ),
        Report(
            id = "REP-005",
            name = "Restaurant Sales Breakdown",
            category = "Restaurant",
            dateRange = "Oct 1 - Oct 31, 2023",
            generatedTime = "Scheduled",
            generatedBy = "System",
            status = ReportStatus.SCHEDULED,
            lastUpdated = "N/A"
        ),
        Report(
            id = "REP-006",
            name = "Guest Demographics",
            category = "Guest",
            dateRange = "YTD 2023",
            generatedTime = "02:45 PM",
            generatedBy = "Marketing",
            status = ReportStatus.GENERATED,
            lastUpdated = "Oct 28, 2023"
        )
    )
}
