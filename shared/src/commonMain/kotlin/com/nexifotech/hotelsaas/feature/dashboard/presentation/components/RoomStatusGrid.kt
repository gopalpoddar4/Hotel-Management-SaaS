package com.nexifotech.hotelsaas.feature.dashboard.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.Neutral10
import com.nexifotech.hotelsaas.core.ui.theme.Neutral20
import com.nexifotech.hotelsaas.core.ui.theme.Neutral90
import com.nexifotech.hotelsaas.core.ui.theme.Neutral95
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RoomStatusMetrics

val StatusVacant        = Color(0xFF4CAF50)
val StatusOccupied      = Color(0xFFF44336)
val StatusMaintenance   = Color(0xFFFF9800)
val StatusDirty         = Color(0xFFFF5722)

@Composable
fun RoomStatusGrid(
    roomStatus: RoomStatusMetrics,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val gridBg = if (isDark) Neutral10 else Neutral95
    val titleColor = if (isDark) Neutral90 else Neutral20

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = gridBg,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Room Status",
                style = MaterialTheme.typography.titleLarge,
                color = titleColor
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Available",
                    value = roomStatus.available.toString(),
                    modifier = Modifier.weight(1f),
                    indicatorColor = StatusVacant
                )
                StatCard(
                    title = "Occupied",
                    value = roomStatus.occupied.toString(),
                    modifier = Modifier.weight(1f),
                    indicatorColor = StatusOccupied
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Dirty",
                    value = roomStatus.dirty.toString(),
                    modifier = Modifier.weight(1f),
                    indicatorColor = StatusDirty
                )
                StatCard(
                    title = "Maintenance",
                    value = roomStatus.maintenance.toString(),
                    modifier = Modifier.weight(1f),
                    indicatorColor = StatusMaintenance
                )
            }
        }
    }
}
