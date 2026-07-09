package com.nexifotech.hotelsaas.feature.reports.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportStatus

@Composable
fun StatusChip(status: ReportStatus, modifier: Modifier = Modifier) {
    val backgroundColor = when (status) {
        ReportStatus.GENERATED -> Color(0xFFE8F5E9) // Light Green
        ReportStatus.PENDING -> Color(0xFFFFF3E0) // Light Orange
        ReportStatus.SCHEDULED -> Color(0xFFE3F2FD) // Light Blue
        ReportStatus.FAILED -> Color(0xFFFFEBEE) // Light Red
    }
    
    val textColor = when (status) {
        ReportStatus.GENERATED -> Color(0xFF2E7D32)
        ReportStatus.PENDING -> Color(0xFFEF6C00)
        ReportStatus.SCHEDULED -> Color(0xFF1565C0)
        ReportStatus.FAILED -> Color(0xFFC62828)
    }

    Text(
        text = status.name,
        color = textColor,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
