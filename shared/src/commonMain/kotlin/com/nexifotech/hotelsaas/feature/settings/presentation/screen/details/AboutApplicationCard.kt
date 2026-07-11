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
fun AboutApplicationCard(
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
                title = "Hotel SaaS Version",
                subtitle = settings.aboutApplication.saasVersion
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Developer",
                subtitle = settings.aboutApplication.developer
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Copyright",
                subtitle = settings.aboutApplication.copyright
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "License",
                subtitle = settings.aboutApplication.license
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Privacy Policy",
                subtitle = settings.aboutApplication.privacyPolicyUrl
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                title = "Terms & Conditions",
                subtitle = settings.aboutApplication.termsUrl
            )
        }
    }
}
