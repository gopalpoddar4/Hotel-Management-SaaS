package com.nexifotech.hotelsaas.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.RevenueCard
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.RoomCard
import com.nexifotech.hotelsaas.core.ui.theme.GuestCard
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
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
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
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                    )
                )
            )
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {

            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
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
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        uiState.roomStatus?.let { RoomStatusGrid(it) }
                        RecentBookingsTable(uiState.recentBookings)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        QuickActions()
                    }
                }
            } else {
                // Mobile/Tablet: Single column layout
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
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
                StatCard(
                    title = "Occupancy",
                    value = "${(metrics.todayOccupancy * 100).toInt()}%",
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Bed,
                    indicatorColor = RoomCard
                )
                StatCard(
                    title = "Revenue",
                    value = "₹${metrics.todayRevenue}",
                    modifier = Modifier.weight(1f),
                    icon = Icons.Rounded.AttachMoney,
                    indicatorColor = RevenueCard
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Arrivals",
                    value = metrics.arrivals.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Luggage,
                    indicatorColor = BookingCard
                )
                StatCard(
                    title = "Departures",
                    value = metrics.departures.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    indicatorColor = GuestCard
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StatCard(
                title = "Occupancy",
                value = "${(metrics.todayOccupancy * 100).toInt()}%",
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Bed,
                indicatorColor = RoomCard
            )
            StatCard(
                title = "Revenue",
                value = "₹${metrics.todayRevenue}",
                modifier = Modifier.weight(1f),
                icon = Icons.Rounded.AttachMoney,
                indicatorColor = RevenueCard
            )
            StatCard(
                title = "Arrivals",
                value = metrics.arrivals.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Luggage,
                indicatorColor = BookingCard
            )
            StatCard(
                title = "Departures",
                value = metrics.departures.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                indicatorColor = GuestCard
            )
        }
    }
}
