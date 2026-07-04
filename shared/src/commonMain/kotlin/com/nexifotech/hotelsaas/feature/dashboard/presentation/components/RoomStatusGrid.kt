package com.nexifotech.hotelsaas.feature.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RoomStatusMetrics

@Composable
fun RoomStatusGrid(
    roomStatus: RoomStatusMetrics,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Room Status",
                style = MaterialTheme.typography.titleLarge
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Available",
                    value = roomStatus.available.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Occupied",
                    value = roomStatus.occupied.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Dirty",
                    value = roomStatus.dirty.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Maintenance",
                    value = roomStatus.maintenance.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
