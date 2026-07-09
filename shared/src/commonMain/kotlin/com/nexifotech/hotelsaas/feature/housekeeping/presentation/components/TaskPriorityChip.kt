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
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskPriority

@Composable
fun TaskPriorityChip(priority: TaskPriority) {
    val (backgroundColor, textColor) = when (priority) {
        TaskPriority.HIGH -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.error
        TaskPriority.MEDIUM -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
        TaskPriority.LOW -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = priority.name,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
