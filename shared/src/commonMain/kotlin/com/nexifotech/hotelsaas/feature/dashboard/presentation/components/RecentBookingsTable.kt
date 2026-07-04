package com.nexifotech.hotelsaas.feature.dashboard.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RecentBooking

@Composable
fun RecentBookingsTable(
    bookings: List<RecentBooking>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Allow horizontal scrolling on smaller screens
            Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Guest Name", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold)
                    Text("Room No", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                    Text("Room Type", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold)
                    Text("Status", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold)
                }
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                
                bookings.forEach { booking ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(booking.guestName, modifier = Modifier.width(150.dp))
                        Text(booking.roomNo, modifier = Modifier.width(100.dp))
                        Text(booking.roomType, modifier = Modifier.width(120.dp))
                        Text(booking.bookingStatus.name, modifier = Modifier.width(120.dp))
                    }
                }
            }
        }
    }
}
