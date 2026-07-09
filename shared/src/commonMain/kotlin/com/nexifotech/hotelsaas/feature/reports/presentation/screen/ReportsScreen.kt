package com.nexifotech.hotelsaas.feature.reports.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.GuestCard
import com.nexifotech.hotelsaas.core.ui.theme.RevenueCard
import com.nexifotech.hotelsaas.core.ui.theme.RoomCard
import com.nexifotech.hotelsaas.feature.reports.presentation.components.EmptyState
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ErrorState
import com.nexifotech.hotelsaas.feature.reports.presentation.components.LoadingView
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ReportCard
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ReportsFilterSection
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ReportsSearchBar
import com.nexifotech.hotelsaas.feature.reports.presentation.components.ReportsSummaryCard
import com.nexifotech.hotelsaas.feature.reports.presentation.event.ReportsEvent
import com.nexifotech.hotelsaas.feature.reports.presentation.state.ReportsUiState
import com.nexifotech.hotelsaas.feature.reports.presentation.viewmodel.ReportsViewModel

@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel = viewModel { ReportsViewModel() },
    onNavigateToReportDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                    )
                )
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Reports & Analytics",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp)
        )
        
        if (uiState.isLoading && uiState.reports.isEmpty()) {
            LoadingView()
        } else if (uiState.error != null && uiState.reports.isEmpty()) {
            ErrorState(message = uiState.error ?: "Unknown error")
        } else {
            ReportsContent(
                uiState = uiState,
                windowSizeClass = windowSizeClass,
                onEvent = viewModel::onEvent,
                onNavigateToReportDetails = onNavigateToReportDetails
            )
        }
    }
}

@Composable
private fun ReportsContent(
    uiState: ReportsUiState,
    windowSizeClass: WindowSizeClass,
    onEvent: (ReportsEvent) -> Unit,
    onNavigateToReportDetails: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Summary Cards Section
        uiState.metrics?.let { metrics ->
            item {
                if (windowSizeClass == WindowSizeClass.Expanded) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ReportsSummaryCard(
                            title = "Today's Revenue",
                            value = "₹${metrics.todaysRevenue}",
                            icon = Icons.Default.AttachMoney,
                            indicatorColor = RevenueCard,
                            modifier = Modifier.weight(1f)
                        )
                        ReportsSummaryCard(
                            title = "Occupancy Rate",
                            value = "${(metrics.occupancyRate * 100).toInt()}%",
                            icon = Icons.Default.Bed,
                            indicatorColor = RoomCard,
                            modifier = Modifier.weight(1f)
                        )
                        ReportsSummaryCard(
                            title = "Total Bookings",
                            value = metrics.totalBookings.toString(),
                            icon = Icons.Default.DateRange,
                            indicatorColor = BookingCard,
                            modifier = Modifier.weight(1f)
                        )
                        ReportsSummaryCard(
                            title = "Total Guests",
                            value = metrics.totalGuests.toString(),
                            icon = Icons.Default.People,
                            indicatorColor = GuestCard,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            ReportsSummaryCard(
                                title = "Today's Revenue",
                                value = "₹${metrics.todaysRevenue}",
                                icon = Icons.Default.AttachMoney,
                                indicatorColor = RevenueCard,
                                modifier = Modifier.weight(1f)
                            )
                            ReportsSummaryCard(
                                title = "Occupancy",
                                value = "${(metrics.occupancyRate * 100).toInt()}%",
                                icon = Icons.Default.Bed,
                                indicatorColor = RoomCard,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            ReportsSummaryCard(
                                title = "Bookings",
                                value = metrics.totalBookings.toString(),
                                icon = Icons.Default.DateRange,
                                indicatorColor = BookingCard,
                                modifier = Modifier.weight(1f)
                            )
                            ReportsSummaryCard(
                                title = "Guests",
                                value = metrics.totalGuests.toString(),
                                icon = Icons.Default.People,
                                indicatorColor = GuestCard,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Search and Filter Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ReportsSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { onEvent(ReportsEvent.SearchChanged(it)) }
                )
                ReportsFilterSection(
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = { onEvent(ReportsEvent.FilterChanged(it)) },
                    selectedDateFilter = uiState.selectedDateFilter,
                    onDateFilterSelected = { onEvent(ReportsEvent.DateFilterChanged(it)) }
                )
            }
        }

        // Reports List
        if (uiState.reports.isEmpty()) {
            item {
                EmptyState(message = "No reports found matching your criteria.")
            }
        } else {
            items(uiState.reports) { report ->
                ReportCard(
                    report = report,
                    onClick = { onNavigateToReportDetails(report.id) },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}
