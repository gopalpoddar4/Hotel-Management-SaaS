package com.nexifotech.hotelsaas.feature.rooms.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.Cleaning
import com.nexifotech.hotelsaas.core.ui.theme.Maintenance
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.core.ui.theme.Reserved
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus

@Composable
fun RoomStatusChip(
    status: RoomStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (status) {
        RoomStatus.AVAILABLE -> Available.copy(alpha = 0.15f) to Available
        RoomStatus.OCCUPIED -> Occupied.copy(alpha = 0.15f) to Occupied
        RoomStatus.RESERVED -> Reserved.copy(alpha = 0.15f) to Reserved
        RoomStatus.CLEANING -> Cleaning.copy(alpha = 0.15f) to Cleaning
        RoomStatus.MAINTENANCE -> Maintenance.copy(alpha = 0.15f) to Maintenance
        RoomStatus.OUT_OF_SERVICE -> Color.Gray.copy(alpha = 0.15f) to Color.Gray
    }

    val statusText = status.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = statusText,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
