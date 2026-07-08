package com.nexifotech.hotelsaas.feature.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.Cleaning
import com.nexifotech.hotelsaas.core.ui.theme.Maintenance
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RoomStatusMetrics

@Composable
fun RoomStatusGrid(
    roomStatus: RoomStatusMetrics,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Room Status",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                title = "Available",
                value = roomStatus.available.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.CheckCircle,
                indicatorColor = Available
            )
            StatCard(
                title = "Occupied",
                value = roomStatus.occupied.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Person,
                indicatorColor = Occupied
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
                icon = Icons.Filled.CleaningServices,
                indicatorColor = Cleaning
            )
            StatCard(
                title = "Maintenance",
                value = roomStatus.maintenance.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Build,
                indicatorColor = Maintenance
            )
        }
    }
}
