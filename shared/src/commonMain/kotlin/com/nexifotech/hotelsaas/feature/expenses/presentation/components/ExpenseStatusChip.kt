package com.nexifotech.hotelsaas.feature.expenses.presentation.components

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
import com.nexifotech.hotelsaas.feature.expenses.domain.model.ApprovalStatus
import com.nexifotech.hotelsaas.feature.expenses.domain.model.PaymentStatus

@Composable
fun ExpenseStatusChip(
    status: Any, // Expects PaymentStatus or ApprovalStatus
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, text) = when (status) {
        is ApprovalStatus -> {
            when (status) {
                ApprovalStatus.APPROVED -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), status.displayName)
                ApprovalStatus.PENDING -> Triple(Color(0xFFFFF3E0), Color(0xFFEF6C00), status.displayName)
                ApprovalStatus.REJECTED -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), status.displayName)
            }
        }
        is PaymentStatus -> {
            when (status) {
                PaymentStatus.PAID -> Triple(Color(0xFFE3F2FD), Color(0xFF1565C0), status.displayName)
                PaymentStatus.PENDING -> Triple(Color(0xFFFFF8E1), Color(0xFFF57F17), status.displayName)
                PaymentStatus.OVERDUE -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), status.displayName)
                PaymentStatus.FAILED -> Triple(Color(0xFFFBE9E7), Color(0xFFD84315), status.displayName)
            }
        }
        else -> Triple(Color.Gray.copy(alpha = 0.1f), Color.Gray, "Unknown")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}
