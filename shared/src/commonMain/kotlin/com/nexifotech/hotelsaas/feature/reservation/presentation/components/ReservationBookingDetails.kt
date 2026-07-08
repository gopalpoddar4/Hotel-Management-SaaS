package com.nexifotech.hotelsaas.feature.reservation.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation

@Composable
fun ReservationBookingDetails(reservation: Reservation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Booking Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Room Type", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.roomType, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Room Number", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.roomNumber, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Check-in", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.checkInDate, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(text = "Check-out", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = reservation.checkOutDate, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Column {
                Text(text = "Special Requests", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = reservation.specialRequests, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
