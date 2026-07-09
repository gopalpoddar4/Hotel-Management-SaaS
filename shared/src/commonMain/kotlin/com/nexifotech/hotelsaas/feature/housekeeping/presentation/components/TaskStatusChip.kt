package com.nexifotech.hotelsaas.feature.housekeeping.presentation.components

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
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus

@Composable
fun TaskStatusChip(status: TaskStatus) {
    val (backgroundColor, textColor) = when (status) {
        TaskStatus.DIRTY -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.error
        TaskStatus.CLEANING -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary
        TaskStatus.READY -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.secondary
        TaskStatus.COMPLETED -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary
        TaskStatus.INSPECTED -> MaterialTheme.colorScheme.tertiary to MaterialTheme.colorScheme.onTertiary
        TaskStatus.MAINTENANCE -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status.name.replace("_", " "),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
