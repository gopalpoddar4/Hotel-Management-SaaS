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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupInfo
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupActionButtons
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupStatusChip
import com.nexifotech.hotelsaas.feature.backup.presentation.components.BackupTypeChip
import com.nexifotech.hotelsaas.feature.backup.presentation.components.TimelineView
import com.nexifotech.hotelsaas.feature.backup.presentation.viewmodel.BackupDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupDetailsScreen(
    backupId: String,
    onBack: () -> Unit,
    viewModel: BackupDetailsViewModel = viewModel(key = backupId) { BackupDetailsViewModel(backupId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Backup Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Box(
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
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
                }
            } else {
                uiState.backup?.let { backup ->
                    BackupDetailsContent(backup = backup, windowSizeClass = windowSizeClass)
                }
            }
        }
    }
}

@Composable
private fun BackupDetailsContent(backup: BackupInfo, windowSizeClass: WindowSizeClass) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            HeaderSection(backup)
        }

        item {
            if (windowSizeClass == WindowSizeClass.Expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        InfoCard(backup)
                        MetadataCard(backup)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        TimelineView(createdAt = backup.createdAt, completedAt = backup.completedAt)
                        BackupActionButtons()
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    InfoCard(backup)
                    MetadataCard(backup)
                    TimelineView(createdAt = backup.createdAt, completedAt = backup.completedAt)
                    BackupActionButtons()
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HeaderSection(backup: BackupInfo) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = backup.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "ID: ${backup.id}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            BackupStatusChip(status = backup.status)
            BackupTypeChip(type = backup.type)
        }
    }
}

@Composable
private fun InfoCard(backup: BackupInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Backup Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            DetailRow(label = "Size", value = backup.size)
            DetailRow(label = "Location", value = backup.location.name.replace("_", " "))
            DetailRow(label = "Database Version", value = backup.databaseVersion)
            DetailRow(label = "Encrypted", value = if (backup.isEncrypted) "Yes" else "No")
            DetailRow(label = "Compressed", value = if (backup.isCompressed) "Yes" else "No")
            DetailRow(label = "Compatibility", value = backup.compatibility)
        }
    }
}

@Composable
private fun MetadataCard(backup: BackupInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Metadata",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            DetailRow(label = "Created By", value = backup.createdBy)
            DetailRow(label = "Created Time", value = backup.createdAt)
            DetailRow(label = "Completed Time", value = backup.completedAt ?: "N/A")
            DetailRow(label = "Duration", value = backup.duration)
            DetailRow(label = "Est. Restore Time", value = backup.restoreTimeEstimated)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
