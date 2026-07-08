package com.nexifotech.hotelsaas.feature.rooms.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomSummaryMetrics
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.Cleaning
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.core.ui.theme.Primary

@Composable
fun RoomSummaryCards(
    metrics: RoomSummaryMetrics,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricCard(title = "Total", value = metrics.totalRooms.toString(), color = Primary, modifier = Modifier.weight(1f))
                MetricCard(title = "Available", value = metrics.availableRooms.toString(), color = Available, modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricCard(title = "Occupied", value = metrics.occupiedRooms.toString(), color = Occupied, modifier = Modifier.weight(1f))
                MetricCard(title = "Cleaning", value = metrics.dirtyRooms.toString(), color = Cleaning, modifier = Modifier.weight(1f))
            }
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(title = "Total", value = metrics.totalRooms.toString(), color = Primary, modifier = Modifier.weight(1f))
            MetricCard(title = "Available", value = metrics.availableRooms.toString(), color = Available, modifier = Modifier.weight(1f))
            MetricCard(title = "Occupied", value = metrics.occupiedRooms.toString(), color = Occupied, modifier = Modifier.weight(1f))
            MetricCard(title = "Cleaning", value = metrics.dirtyRooms.toString(), color = Cleaning, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
