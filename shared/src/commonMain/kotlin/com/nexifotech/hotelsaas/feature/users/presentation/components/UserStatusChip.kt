package com.nexifotech.hotelsaas.feature.users.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.users.domain.model.UserStatus
import com.nexifotech.hotelsaas.core.ui.theme.Online
import com.nexifotech.hotelsaas.core.ui.theme.Offline
import com.nexifotech.hotelsaas.core.ui.theme.Reserved
import com.nexifotech.hotelsaas.core.ui.theme.Maintenance

@Composable
fun UserStatusChip(
    status: UserStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (status) {
        UserStatus.ACTIVE -> Online.copy(alpha = 0.2f) to Online
        UserStatus.INACTIVE -> Offline.copy(alpha = 0.2f) to Offline
        UserStatus.SUSPENDED -> Reserved.copy(alpha = 0.2f) to Reserved
        UserStatus.LOCKED -> Maintenance.copy(alpha = 0.2f) to Maintenance
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.displayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
