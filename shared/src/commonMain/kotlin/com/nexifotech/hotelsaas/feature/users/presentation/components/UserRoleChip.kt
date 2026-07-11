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
import com.nexifotech.hotelsaas.feature.users.domain.model.UserRole
import com.nexifotech.hotelsaas.core.ui.theme.StaffCard
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.RevenueCard

@Composable
fun UserRoleChip(
    role: UserRole,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (role) {
        UserRole.ADMIN -> RevenueCard.copy(alpha = 0.2f) to RevenueCard
        UserRole.MANAGER -> BookingCard.copy(alpha = 0.2f) to BookingCard
        UserRole.STAFF, UserRole.RECEPTIONIST, UserRole.HOUSEKEEPER, UserRole.ACCOUNTANT, UserRole.MAINTENANCE -> StaffCard.copy(alpha = 0.2f) to StaffCard
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = role.displayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
