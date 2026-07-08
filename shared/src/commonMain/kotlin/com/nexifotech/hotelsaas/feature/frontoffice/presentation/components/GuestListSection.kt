package com.nexifotech.hotelsaas.feature.frontoffice.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest

@Composable
fun GuestListSection(
    guests: List<Guest>,
    onGuestClicked: (Guest) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Today's Guests",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (guests.isEmpty()) {
            EmptyState()
        } else {
            guests.forEach { guest ->
                GuestCardItem(
                    guest = guest,
                    onClick = { onGuestClicked(guest) }
                )
            }
        }
    }
}
