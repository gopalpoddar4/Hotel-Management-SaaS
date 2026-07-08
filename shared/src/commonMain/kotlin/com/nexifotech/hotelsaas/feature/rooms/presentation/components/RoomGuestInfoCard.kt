package com.nexifotech.hotelsaas.feature.rooms.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room

@Composable
fun RoomGuestInfoCard(room: Room) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Guest & Housekeeping",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            if (room.currentGuestName != null) {
                InfoItem(
                    icon = Icons.Default.Person,
                    label = "Current Guest",
                    value = room.currentGuestName
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(
                    icon = Icons.Default.DateRange,
                    label = "Stay",
                    value = "${room.checkInDate?.take(10) ?: "N/A"} to ${room.expectedCheckOutDate?.take(10) ?: "N/A"}"
                )
            } else {
                Text(
                    text = "No guest currently assigned.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(16.dp))

            InfoItem(
                icon = Icons.Default.CheckCircle, // using CheckCircle as placeholder for cleaning
                label = "Housekeeping Status",
                value = room.housekeepingStatus,
                valueColor = if (room.housekeepingStatus == "Clean") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem(
                icon = Icons.Default.Refresh,
                label = "Last Cleaned",
                value = room.lastCleaningDate.take(10)
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem(
                icon = Icons.Default.Build,
                label = "Last Maintenance",
                value = room.lastMaintenanceDate.take(10)
            )
        }
    }
}
