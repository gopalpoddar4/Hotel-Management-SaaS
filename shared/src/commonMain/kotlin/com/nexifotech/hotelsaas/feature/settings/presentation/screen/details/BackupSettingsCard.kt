package com.nexifotech.hotelsaas.feature.settings.presentation.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings
import com.nexifotech.hotelsaas.feature.settings.presentation.event.SettingsEvent

@Composable
fun BackupSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var autoBackup by remember { mutableStateOf(settings.backup.autoBackupEnabled) }
    var backupFrequency by remember { mutableStateOf(settings.backup.backupFrequency) }
    var backupLocation by remember { mutableStateOf(settings.backup.backupLocation) }
    var backupRetention by remember { mutableStateOf(settings.backup.backupRetentionDays.toString()) }

    LaunchedEffect(settings.backup) {
        autoBackup = settings.backup.autoBackupEnabled
        backupFrequency = settings.backup.backupFrequency
        backupLocation = settings.backup.backupLocation
        backupRetention = settings.backup.backupRetentionDays.toString()
    }

    val updateSettings = {
        val updated = settings.copy(
            backup = settings.backup.copy(
                autoBackupEnabled = autoBackup,
                backupFrequency = backupFrequency,
                backupLocation = backupLocation,
                backupRetentionDays = backupRetention.toIntOrNull() ?: settings.backup.backupRetentionDays
            )
        )
        onEvent(SettingsEvent.UpdateSettings(updated))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("Auto Backup Enabled", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(checked = autoBackup, onCheckedChange = { autoBackup = it; updateSettings() })
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = backupFrequency,
                    onValueChange = { backupFrequency = it; updateSettings() },
                    label = { Text("Backup Frequency") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = backupRetention,
                    onValueChange = { backupRetention = it; updateSettings() },
                    label = { Text("Retention Days") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = backupLocation,
                onValueChange = { backupLocation = it; updateSettings() },
                label = { Text("Backup Location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}
