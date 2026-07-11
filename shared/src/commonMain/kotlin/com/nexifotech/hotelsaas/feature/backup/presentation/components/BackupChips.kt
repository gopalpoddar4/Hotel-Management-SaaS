package com.nexifotech.hotelsaas.feature.backup.presentation.components

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
import com.nexifotech.hotelsaas.core.ui.theme.Error
import com.nexifotech.hotelsaas.core.ui.theme.Warning
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupStatus
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupType

@Composable
fun BackupStatusChip(status: BackupStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        BackupStatus.SUCCESS -> Triple(Available.copy(alpha = 0.15f), Available, "Success")
        BackupStatus.FAILED -> Triple(Error.copy(alpha = 0.15f), Error, "Failed")
        BackupStatus.IN_PROGRESS -> Triple(Warning.copy(alpha = 0.15f), Warning, "In Progress")
        BackupStatus.PENDING -> Triple(Color.Gray.copy(alpha = 0.15f), Color.Gray, "Pending")
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
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

@Composable
fun BackupTypeChip(type: BackupType) {
    val (backgroundColor, textColor, text) = when (type) {
        BackupType.FULL -> Triple(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), MaterialTheme.colorScheme.primary, "Full")
        BackupType.INCREMENTAL -> Triple(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f), MaterialTheme.colorScheme.secondary, "Incremental")
        BackupType.DATABASE_ONLY -> Triple(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f), MaterialTheme.colorScheme.tertiary, "Database")
        BackupType.MEDIA_ONLY -> Triple(Color.Magenta.copy(alpha = 0.15f), Color.Magenta, "Media")
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
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
