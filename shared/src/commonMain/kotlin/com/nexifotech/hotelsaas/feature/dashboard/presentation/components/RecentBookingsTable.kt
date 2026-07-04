package com.nexifotech.hotelsaas.feature.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.Navy10
import com.nexifotech.hotelsaas.core.ui.theme.Navy30
import com.nexifotech.hotelsaas.core.ui.theme.Navy90
import com.nexifotech.hotelsaas.core.ui.theme.Neutral10
import com.nexifotech.hotelsaas.core.ui.theme.Neutral20
import com.nexifotech.hotelsaas.core.ui.theme.Neutral90
import com.nexifotech.hotelsaas.core.ui.theme.Neutral99
import com.nexifotech.hotelsaas.core.ui.theme.Red40
import com.nexifotech.hotelsaas.core.ui.theme.Red90
import com.nexifotech.hotelsaas.core.ui.theme.StatusDirty
import com.nexifotech.hotelsaas.core.ui.theme.StatusMaintenance
import com.nexifotech.hotelsaas.core.ui.theme.StatusVacant
import com.nexifotech.hotelsaas.feature.dashboard.domain.model.RecentBooking

@Composable
fun StatusBadge(status: String) {
    val isDark = isSystemInDarkTheme()
    val (bgColor, textColor) = when (status.uppercase()) {
        "CHECKED_IN", "CONFIRMED" -> StatusVacant.copy(alpha = 0.2f) to StatusVacant
        "PENDING" -> StatusMaintenance.copy(alpha = 0.2f) to StatusMaintenance
        "CANCELLED" -> StatusDirty.copy(alpha = 0.2f) to StatusDirty
        else -> (if (isDark) Neutral20 else Neutral90) to (if (isDark) Neutral90 else Neutral20)
    }
    
    Box(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RecentBookingsTable(
    bookings: List<RecentBooking>,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val cardBg = if (isDark) Neutral20 else Neutral99
    val headerBg = if (isDark) Navy30 else Navy90
    val headerText = if (isDark) Navy90 else Navy10
    val rowText = if (isDark) Neutral90 else Neutral10
    val dividerColor = if (isDark) Neutral20 else Neutral90

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBg,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBg)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Activity",
                    style = MaterialTheme.typography.titleLarge,
                    color = headerText
                )
            }
            
            // Allow horizontal scrolling on smaller screens
            Column(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Guest Name", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold, color = rowText)
                    Text("Room No", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold, color = rowText)
                    Text("Room Type", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold, color = rowText)
                    Text("Status", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold, color = rowText)
                }
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = dividerColor)
                
                bookings.forEach { booking ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(booking.guestName, modifier = Modifier.width(150.dp), color = rowText)
                        Text(booking.roomNo, modifier = Modifier.width(100.dp), color = rowText)
                        Text(booking.roomType, modifier = Modifier.width(120.dp), color = rowText)
                        Box(modifier = Modifier.width(120.dp)) {
                            StatusBadge(booking.bookingStatus.name)
                        }
                    }
                }
            }
        }
    }
}
