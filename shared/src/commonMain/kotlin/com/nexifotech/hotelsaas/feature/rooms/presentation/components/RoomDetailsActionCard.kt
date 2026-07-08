package com.nexifotech.hotelsaas.feature.rooms.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus

@Composable
fun RoomDetailsActionCard(
    room: Room,
    onAssignGuest: () -> Unit,
    onReleaseRoom: () -> Unit,
    onChangeStatus: (RoomStatus) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (room.status == RoomStatus.AVAILABLE || room.status == RoomStatus.CLEANING) {
                Button(
                    onClick = onAssignGuest,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text("Assign Guest")
                }
            }

            if (room.status == RoomStatus.OCCUPIED) {
                Button(
                    onClick = onReleaseRoom,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text("Check Out / Release")
                }
            }

            OutlinedButton(
                onClick = { onChangeStatus(RoomStatus.CLEANING) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Mark as Cleaning")
            }

            OutlinedButton(
                onClick = { onChangeStatus(RoomStatus.MAINTENANCE) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Mark for Maintenance")
            }
            
            OutlinedButton(
                onClick = { onChangeStatus(RoomStatus.AVAILABLE) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Mark as Available")
            }
        }
    }
}
