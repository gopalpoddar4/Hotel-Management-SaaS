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
fun SecuritySettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var passwordPolicy by remember { mutableStateOf(settings.security.passwordPolicy) }
    var sessionTimeout by remember { mutableStateOf(settings.security.sessionTimeoutMinutes.toString()) }
    var twoFactorAuth by remember { mutableStateOf(settings.security.twoFactorAuthEnabled) }
    var maxLoginAttempts by remember { mutableStateOf(settings.security.maxLoginAttempts.toString()) }
    var deviceManagement by remember { mutableStateOf(settings.security.deviceManagementEnabled) }

    LaunchedEffect(settings.security) {
        passwordPolicy = settings.security.passwordPolicy
        sessionTimeout = settings.security.sessionTimeoutMinutes.toString()
        twoFactorAuth = settings.security.twoFactorAuthEnabled
        maxLoginAttempts = settings.security.maxLoginAttempts.toString()
        deviceManagement = settings.security.deviceManagementEnabled
    }

    val updateSettings = {
        val updated = settings.copy(
            security = settings.security.copy(
                passwordPolicy = passwordPolicy,
                sessionTimeoutMinutes = sessionTimeout.toIntOrNull() ?: settings.security.sessionTimeoutMinutes,
                twoFactorAuthEnabled = twoFactorAuth,
                maxLoginAttempts = maxLoginAttempts.toIntOrNull() ?: settings.security.maxLoginAttempts,
                deviceManagementEnabled = deviceManagement
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
            OutlinedTextField(
                value = passwordPolicy,
                onValueChange = { passwordPolicy = it; updateSettings() },
                label = { Text("Password Policy") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = sessionTimeout,
                    onValueChange = { sessionTimeout = it; updateSettings() },
                    label = { Text("Session Timeout (Mins)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = maxLoginAttempts,
                    onValueChange = { maxLoginAttempts = it; updateSettings() },
                    label = { Text("Max Login Attempts") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("Two Factor Authentication", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(checked = twoFactorAuth, onCheckedChange = { twoFactorAuth = it; updateSettings() })
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("Device Management", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(checked = deviceManagement, onCheckedChange = { deviceManagement = it; updateSettings() })
            }
        }
    }
}
