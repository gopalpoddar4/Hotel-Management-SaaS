package com.nexifotech.hotelsaas.feature.restaurant.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.*
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.OrderStatus
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.TableStatus
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.PaymentStatus

@Composable
fun TableStatusChip(status: TableStatus) {
    val (bgColor, textColor) = when (status) {
        TableStatus.AVAILABLE -> Pair(SuccessContainer, Success)
        TableStatus.OCCUPIED -> Pair(ErrorContainer, Error)
        TableStatus.RESERVED -> Pair(WarningContainer, Warning)
        TableStatus.CLEANING -> Pair(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary)
    }
    Chip(text = status.name, bgColor = bgColor, textColor = textColor)
}

@Composable
fun OrderStatusChip(status: OrderStatus) {
    val (bgColor, textColor) = when (status) {
        OrderStatus.PREPARING -> Pair(WarningContainer, Warning)
        OrderStatus.READY -> Pair(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary)
        OrderStatus.SERVED -> Pair(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.secondary)
        OrderStatus.COMPLETED -> Pair(SuccessContainer, Success)
        OrderStatus.CANCELLED -> Pair(ErrorContainer, Error)
    }
    Chip(text = status.name, bgColor = bgColor, textColor = textColor)
}

@Composable
fun PaymentStatusChip(status: PaymentStatus) {
    val (bgColor, textColor) = when (status) {
        PaymentStatus.PAID -> Pair(SuccessContainer, Success)
        PaymentStatus.PENDING -> Pair(WarningContainer, Warning)
    }
    Chip(text = status.name, bgColor = bgColor, textColor = textColor)
}

@Composable
private fun Chip(text: String, bgColor: Color, textColor: Color) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
