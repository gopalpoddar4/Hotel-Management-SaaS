package com.nexifotech.hotelsaas.feature.settings.presentation.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings
import com.nexifotech.hotelsaas.feature.settings.presentation.components.SettingsItem

@Composable
fun SystemInformationCard(
    settings: AppSettings
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            SettingsItem(
                title = "Application Version",
                subtitle = settings.systemInformation.applicationVersion
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Build Number",
                subtitle = settings.systemInformation.buildNumber
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Database Version",
                subtitle = settings.systemInformation.databaseVersion
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Last Update",
                subtitle = settings.systemInformation.lastUpdate
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Server Status",
                subtitle = settings.systemInformation.serverStatus
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Platform",
                subtitle = settings.systemInformation.platform
            )
        }
    }
}
