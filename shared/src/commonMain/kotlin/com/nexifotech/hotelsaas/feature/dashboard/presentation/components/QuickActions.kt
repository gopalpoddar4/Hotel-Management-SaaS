package com.nexifotech.hotelsaas.feature.dashboard.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.theme.Gold10
import com.nexifotech.hotelsaas.core.ui.theme.Gold90
import com.nexifotech.hotelsaas.core.ui.theme.Navy40
import com.nexifotech.hotelsaas.core.ui.theme.Neutral20
import com.nexifotech.hotelsaas.core.ui.theme.Neutral90
import com.nexifotech.hotelsaas.core.ui.theme.Neutral95
import com.nexifotech.hotelsaas.core.ui.theme.Neutral99

@Composable
fun QuickActions(
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val cardBg = if (isDark) Neutral20 else Neutral95
    val titleColor = if (isDark) Neutral90 else Neutral20

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBg,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                color = titleColor
            )
            
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy40,
                    contentColor = Neutral99
                )
            ) {
                Text("New Reservation")
            }
            
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold90,
                    contentColor = Gold10
                )
            ) {
                Text("Check-in Guest")
            }
        }
    }
}
