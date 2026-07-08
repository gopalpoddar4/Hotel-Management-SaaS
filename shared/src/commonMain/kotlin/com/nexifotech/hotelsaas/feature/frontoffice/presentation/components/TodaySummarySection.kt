package com.nexifotech.hotelsaas.feature.frontoffice.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.Available
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.Occupied
import com.nexifotech.hotelsaas.core.ui.theme.Reserved
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.StatCard
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.FrontOfficeMetrics

@Composable
fun TodaySummarySection(
    metrics: FrontOfficeMetrics?,
    windowSizeClass: WindowSizeClass
) {
    if (metrics == null) return

    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Check-ins",
                    value = metrics.checkIns.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Filled.Login,
                    indicatorColor = Available
                )
                StatCard(
                    title = "Check-outs",
                    value = metrics.checkOuts.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    indicatorColor = Occupied
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Walk-ins",
                    value = metrics.walkIns.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Person,
                    indicatorColor = BookingCard
                )
                StatCard(
                    title = "Pending Arrivals",
                    value = metrics.pendingArrivals.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Bed,
                    indicatorColor = Reserved
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StatCard(
                title = "Check-ins",
                value = metrics.checkIns.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.Login,
                indicatorColor = Available
            )
            StatCard(
                title = "Check-outs",
                value = metrics.checkOuts.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                indicatorColor = Occupied
            )
            StatCard(
                title = "Walk-ins",
                value = metrics.walkIns.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Person,
                indicatorColor = BookingCard
            )
            StatCard(
                title = "Pending Arrivals",
                value = metrics.pendingArrivals.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Bed,
                indicatorColor = Reserved
            )
        }
    }
}
