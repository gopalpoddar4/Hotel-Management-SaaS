package com.nexifotech.hotelsaas.feature.backup.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupHistoryItem
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupScheduleCard
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupSearchBar
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupSummarySection
import com.nexifotech.hotelsaas.feature.backup.presentation.components.StorageUsageCard
import com.nexifotech.hotelsaas.feature.backup.presentation.state.BackupUiState
import com.nexifotech.hotelsaas.feature.backup.presentation.viewmodel.BackupViewModel

@Composable
fun BackupScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: BackupViewModel = viewModel { BackupViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (uiState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        }
    } else {
        BackupContent(
            uiState = uiState,
            windowSizeClass = windowSizeClass,
            onQueryChange = viewModel::updateSearchQuery,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}

@Composable
private fun BackupContent(
    uiState: BackupUiState,
    windowSizeClass: WindowSizeClass,
    onQueryChange: (String) -> Unit,
    onNavigateToDetails: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                    )
                )
            )
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Backup & Restore",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
        }

        item {
            BackupSummarySection(uiState = uiState, windowSizeClass = windowSizeClass)
        }

        item {
            if (windowSizeClass == WindowSizeClass.Expanded) {
                // Desktop: Split Layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        BackupSearchBar(query = uiState.searchQuery, onQueryChange = onQueryChange)
                        BackupHistorySection(uiState, onNavigateToDetails)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        StorageUsageCard(storageUsage = uiState.storageUsage)
                        BackupScheduleCard(schedule = uiState.schedule)
                    }
                }
            } else {
                // Mobile / Tablet: Single Column
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    StorageUsageCard(storageUsage = uiState.storageUsage)
                    BackupScheduleCard(schedule = uiState.schedule)
                    BackupSearchBar(query = uiState.searchQuery, onQueryChange = onQueryChange)
                    BackupHistorySection(uiState, onNavigateToDetails)
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BackupHistorySection(
    uiState: BackupUiState,
    onNavigateToDetails: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Backup History",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        val filteredBackups = if (uiState.searchQuery.isBlank()) {
            uiState.backups
        } else {
            uiState.backups.filter {
                it.name.contains(uiState.searchQuery, ignoreCase = true) ||
                it.id.contains(uiState.searchQuery, ignoreCase = true) ||
                it.createdBy.contains(uiState.searchQuery, ignoreCase = true)
            }
        }

        if (filteredBackups.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No backups found.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            filteredBackups.forEach { backup ->
                BackupHistoryItem(
                    backup = backup,
                    onClick = { onNavigateToDetails(backup.id) }
                )
            }
        }
    }
}
