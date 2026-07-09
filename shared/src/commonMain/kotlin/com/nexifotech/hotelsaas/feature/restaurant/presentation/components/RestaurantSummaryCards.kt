package com.nexifotech.hotelsaas.feature.restaurant.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.*
import com.nexifotech.hotelsaas.feature.restaurant.domain.model.RestaurantSummary

@Composable
fun RestaurantSummaryCards(
    summary: RestaurantSummary,
    windowSizeClass: WindowSizeClass
) {
    val isMobile = windowSizeClass == WindowSizeClass.Compact
    val columns = if (isMobile) 2 else 4

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (columns == 2) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                SummaryCard(
                    title = "Active Orders",
                    value = summary.activeOrders.toString(),
                    color = Primary,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Revenue",
                    value = "$${summary.todaysRevenue}",
                    color = RevenueCard,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                SummaryCard(
                    title = "Occupied",
                    value = summary.occupiedTables.toString(),
                    color = Occupied,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Available",
                    value = summary.availableTables.toString(),
                    color = Available,
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                SummaryCard(
                    title = "Active Orders",
                    value = summary.activeOrders.toString(),
                    color = Primary,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Occupied Tables",
                    value = summary.occupiedTables.toString(),
                    color = Occupied,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Available Tables",
                    value = summary.availableTables.toString(),
                    color = Available,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Today's Revenue",
                    value = "$${summary.todaysRevenue}",
                    color = RevenueCard,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}
