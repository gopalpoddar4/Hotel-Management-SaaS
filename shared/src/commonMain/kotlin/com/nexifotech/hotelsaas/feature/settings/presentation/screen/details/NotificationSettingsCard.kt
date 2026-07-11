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
fun NotificationSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var emailEnabled by remember { mutableStateOf(settings.notification.emailNotificationsEnabled) }
    var smsEnabled by remember { mutableStateOf(settings.notification.smsNotificationsEnabled) }
    var pushEnabled by remember { mutableStateOf(settings.notification.pushNotificationsEnabled) }
    var reminderSettings by remember { mutableStateOf(settings.notification.reminderSettings) }

    LaunchedEffect(settings.notification) {
        emailEnabled = settings.notification.emailNotificationsEnabled
        smsEnabled = settings.notification.smsNotificationsEnabled
        pushEnabled = settings.notification.pushNotificationsEnabled
        reminderSettings = settings.notification.reminderSettings
    }

    val updateSettings = {
        val updated = settings.copy(
            notification = settings.notification.copy(
                emailNotificationsEnabled = emailEnabled,
                smsNotificationsEnabled = smsEnabled,
                pushNotificationsEnabled = pushEnabled,
                reminderSettings = reminderSettings
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
                Text("Email Notifications", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(checked = emailEnabled, onCheckedChange = { emailEnabled = it; updateSettings() })
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("SMS Notifications", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(checked = smsEnabled, onCheckedChange = { smsEnabled = it; updateSettings() })
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("Push Notifications", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(checked = pushEnabled, onCheckedChange = { pushEnabled = it; updateSettings() })
            }
            OutlinedTextField(
                value = reminderSettings,
                onValueChange = { reminderSettings = it; updateSettings() },
                label = { Text("Reminder Settings") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}
