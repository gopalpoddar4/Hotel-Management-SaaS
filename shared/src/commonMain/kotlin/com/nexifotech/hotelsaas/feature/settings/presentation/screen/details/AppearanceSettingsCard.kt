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
fun AppearanceSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var theme by remember { mutableStateOf(settings.appearance.theme) }
    var accentColor by remember { mutableStateOf(settings.appearance.accentColor) }
    var fontSize by remember { mutableStateOf(settings.appearance.fontSize) }
    var compactMode by remember { mutableStateOf(settings.appearance.compactMode) }
    var dashboardLayout by remember { mutableStateOf(settings.appearance.dashboardLayout) }

    LaunchedEffect(settings.appearance) {
        theme = settings.appearance.theme
        accentColor = settings.appearance.accentColor
        fontSize = settings.appearance.fontSize
        compactMode = settings.appearance.compactMode
        dashboardLayout = settings.appearance.dashboardLayout
    }

    val updateSettings = {
        val updated = settings.copy(
            appearance = settings.appearance.copy(
                theme = theme,
                accentColor = accentColor,
                fontSize = fontSize,
                compactMode = compactMode,
                dashboardLayout = dashboardLayout
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
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = theme,
                    onValueChange = { theme = it; updateSettings() },
                    label = { Text("Theme") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = accentColor,
                    onValueChange = { accentColor = it; updateSettings() },
                    label = { Text("Accent Color") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = fontSize,
                    onValueChange = { fontSize = it; updateSettings() },
                    label = { Text("Font Size") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = dashboardLayout,
                    onValueChange = { dashboardLayout = it; updateSettings() },
                    label = { Text("Dashboard Layout") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Compact Mode",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = compactMode,
                    onCheckedChange = { compactMode = it; updateSettings() }
                )
            }
        }
    }
}
