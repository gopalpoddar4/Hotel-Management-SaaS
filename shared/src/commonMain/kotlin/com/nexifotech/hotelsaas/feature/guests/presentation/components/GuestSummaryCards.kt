package com.nexifotech.hotelsaas.feature.guests.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.core.ui.theme.Primary
import com.nexifotech.hotelsaas.core.ui.theme.GuestCard
import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestSummaryMetrics

@Composable
fun GuestSummaryCards(
    metrics: GuestSummaryMetrics,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricCard(title = "Total Guests", value = metrics.totalGuests.toString(), color = Primary, modifier = Modifier.weight(1f))
                MetricCard(title = "Checked In", value = metrics.checkedIn.toString(), color = Available, modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricCard(title = "Checked Out", value = metrics.checkedOut.toString(), color = Occupied, modifier = Modifier.weight(1f))
                MetricCard(title = "VIP Guests", value = metrics.vipGuests.toString(), color = GuestCard, modifier = Modifier.weight(1f))
            }
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(title = "Total Guests", value = metrics.totalGuests.toString(), color = Primary, modifier = Modifier.weight(1f))
            MetricCard(title = "Checked In", value = metrics.checkedIn.toString(), color = Available, modifier = Modifier.weight(1f))
            MetricCard(title = "Checked Out", value = metrics.checkedOut.toString(), color = Occupied, modifier = Modifier.weight(1f))
            MetricCard(title = "VIP Guests", value = metrics.vipGuests.toString(), color = GuestCard, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
