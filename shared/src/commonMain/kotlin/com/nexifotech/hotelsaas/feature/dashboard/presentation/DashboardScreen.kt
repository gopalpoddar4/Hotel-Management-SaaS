package com.nexifotech.hotelsaas.feature.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.QuickActions
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.RecentBookingsTable
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.RoomStatusGrid
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.StatCard

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel { DashboardViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        }
    } else {
        DashboardContent(
            uiState = uiState,
            windowSizeClass = windowSizeClass
        )
    }
}

@Composable
private fun DashboardContent(
    uiState: DashboardUiState,
    windowSizeClass: WindowSizeClass
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            TopMetricsSection(uiState)
        }

        item {
            if (windowSizeClass == WindowSizeClass.Expanded) {
                // Desktop/Web: Split layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        uiState.roomStatus?.let { RoomStatusGrid(it) }
                        RecentBookingsTable(uiState.recentBookings)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        QuickActions()
                    }
                }
            } else {
                // Mobile/Tablet: Single column layout
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.roomStatus?.let { RoomStatusGrid(it) }
                    QuickActions()
                    RecentBookingsTable(uiState.recentBookings)
                }
            }
        }
    }
}

@Composable
private fun TopMetricsSection(uiState: DashboardUiState) {
    val windowSizeClass = rememberWindowSizeClass()
    val metrics = uiState.metrics ?: return

    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Occupancy", "${(metrics.todayOccupancy * 100).toInt()}%", Modifier.weight(1f))
                StatCard("Revenue", "₹${metrics.todayRevenue}", Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Arrivals", metrics.arrivals.toString(), Modifier.weight(1f))
                StatCard("Departures", metrics.departures.toString(), Modifier.weight(1f))
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard("Occupancy", "${(metrics.todayOccupancy * 100).toInt()}%", Modifier.weight(1f))
            StatCard("Revenue", "₹${metrics.todayRevenue}", Modifier.weight(1f))
            StatCard("Arrivals", metrics.arrivals.toString(), Modifier.weight(1f))
            StatCard("Departures", metrics.departures.toString(), Modifier.weight(1f))
        }
    }
}
