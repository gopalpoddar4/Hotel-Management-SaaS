package com.nexifotech.hotelsaas.feature.housekeeping.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.StatCard
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary

@Composable
fun HousekeepingSummaryCard(
    summary: HousekeepingSummary,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = rememberWindowSizeClass()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Today's Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (windowSizeClass == WindowSizeClass.Expanded) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Pending Tasks",
                    value = summary.pendingTasks.toString(),
                    icon = Icons.Default.Home,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Completed Today",
                    value = summary.completedToday.toString(),
                    icon = Icons.Default.CheckCircle,
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Maintenance",
                    value = summary.maintenanceRequests.toString(),
                    icon = Icons.Default.Build,
                    indicatorColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Room Status",
                    value = summary.totalRooms.toString(),
                    subtitle = "Clean: ${summary.cleanRooms} | Dirty: ${summary.dirtyRooms}",
                    icon = Icons.Default.CleaningServices,
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Pending Tasks",
                        value = summary.pendingTasks.toString(),
                        icon = Icons.Default.Home,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Completed Today",
                        value = summary.completedToday.toString(),
                        icon = Icons.Default.CheckCircle,
                        indicatorColor = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Maintenance",
                        value = summary.maintenanceRequests.toString(),
                        icon = Icons.Default.Build,
                        indicatorColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Room Status",
                        value = summary.totalRooms.toString(),
                        subtitle = "Clean: ${summary.cleanRooms} | Dirty: ${summary.dirtyRooms}",
                        icon = Icons.Default.CleaningServices,
                        indicatorColor = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
