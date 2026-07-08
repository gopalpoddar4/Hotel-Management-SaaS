package com.nexifotech.hotelsaas.feature.frontoffice.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.core.ui.theme.Reserved

@Composable
fun StatusChip(status: String) {
    val (containerColor, contentColor) = when (status) {
        "Checked In" -> Available.copy(alpha = 0.15f) to Available
        "Reserved", "Expected" -> Reserved.copy(alpha = 0.15f) to Reserved
        "VIP" -> BookingCard.copy(alpha = 0.15f) to BookingCard
        "Checked Out" -> Occupied.copy(alpha = 0.15f) to Occupied
        else -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = contentColor
        )
    }
}
