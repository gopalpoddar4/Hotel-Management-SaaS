package com.nexifotech.hotelsaas.feature.reservation.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.core.ui.theme.Reserved
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.StatCard
import com.nexifotech.hotelsaas.feature.reservation.domain.model.ReservationSummaryMetrics

@Composable
fun ReservationSummaryCards(
    metrics: ReservationSummaryMetrics?,
    windowSizeClass: WindowSizeClass
) {
    if (metrics == null) return

    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Total",
                    value = metrics.totalReservations.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.DateRange,
                    indicatorColor = BookingCard
                )
                StatCard(
                    title = "Pending",
                    value = metrics.pendingConfirmations.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.HourglassEmpty,
                    indicatorColor = Reserved
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Check-ins",
                    value = metrics.todaysCheckIns.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Filled.Login,
                    indicatorColor = Available
                )
                StatCard(
                    title = "Check-outs",
                    value = metrics.todaysCheckOuts.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    indicatorColor = Occupied
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StatCard(
                title = "Total",
                value = metrics.totalReservations.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Default.DateRange,
                indicatorColor = BookingCard
            )
            StatCard(
                title = "Pending",
                value = metrics.pendingConfirmations.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Default.HourglassEmpty,
                indicatorColor = Reserved
            )
            StatCard(
                title = "Check-ins",
                value = metrics.todaysCheckIns.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.Login,
                indicatorColor = Available
            )
            StatCard(
                title = "Check-outs",
                value = metrics.todaysCheckOuts.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                indicatorColor = Occupied
            )
        }
    }
}
