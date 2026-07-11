package com.nexifotech.hotelsaas.feature.backup.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.theme.BookingCard
import com.nexifotech.hotelsaas.core.ui.theme.Error
import com.nexifotech.hotelsaas.core.ui.theme.RevenueCard
import com.nexifotech.hotelsaas.core.ui.theme.Success
import com.nexifotech.hotelsaas.feature.backup.presentation.state.BackupUiState
import com.nexifotech.hotelsaas.feature.dashboard.presentation.components.StatCard

@Composable
fun BackupSummarySection(uiState: BackupUiState, windowSizeClass: WindowSizeClass) {
    if (windowSizeClass == WindowSizeClass.Compact) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Total Backups",
                    value = uiState.totalBackups.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Storage,
                    indicatorColor = RevenueCard
                )
                StatCard(
                    title = "Successful",
                    value = uiState.successfulBackups.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.CloudDone,
                    indicatorColor = Success
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Failed Backups",
                    value = uiState.failedBackups.toString(),
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Warning,
                    indicatorColor = Error
                )
                StatCard(
                    title = "Cloud Usage",
                    value = uiState.storageUsage?.cloudStorageUsed ?: "0 GB",
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.DataUsage,
                    indicatorColor = BookingCard
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StatCard(
                title = "Total Backups",
                value = uiState.totalBackups.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Storage,
                indicatorColor = RevenueCard
            )
            StatCard(
                title = "Successful",
                value = uiState.successfulBackups.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.CloudDone,
                indicatorColor = Success
            )
            StatCard(
                title = "Failed",
                value = uiState.failedBackups.toString(),
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Warning,
                indicatorColor = Error
            )
            StatCard(
                title = "Cloud Usage",
                value = uiState.storageUsage?.cloudStorageUsed ?: "0 GB",
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.DataUsage,
                indicatorColor = BookingCard
            )
        }
    }
}
