package com.nexifotech.hotelsaas.feature.settings.presentation.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings
import com.nexifotech.hotelsaas.feature.settings.presentation.event.SettingsEvent

@Composable
fun EmailSmsSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var smtpConfig by remember { mutableStateOf(settings.emailSms.smtpConfiguration) }
    var senderName by remember { mutableStateOf(settings.emailSms.senderName) }

    LaunchedEffect(settings.emailSms) {
        smtpConfig = settings.emailSms.smtpConfiguration
        senderName = settings.emailSms.senderName
    }

    val updateSettings = {
        val updated = settings.copy(
            emailSms = settings.emailSms.copy(
                smtpConfiguration = smtpConfig,
                senderName = senderName
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
                value = smtpConfig,
                onValueChange = { smtpConfig = it; updateSettings() },
                label = { Text("SMTP Configuration") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = senderName,
                onValueChange = { senderName = it; updateSettings() },
                label = { Text("Sender Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Text(
                text = "Email Templates Configured: ${settings.emailSms.emailTemplatesConfigured}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "SMS Templates Configured: ${settings.emailSms.smsTemplatesConfigured}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
