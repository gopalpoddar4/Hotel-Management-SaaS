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
fun GeneralSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var dateFormat by remember { mutableStateOf(settings.general.dateFormat) }
    var timeFormat by remember { mutableStateOf(settings.general.timeFormat) }
    var language by remember { mutableStateOf(settings.general.language) }
    var currency by remember { mutableStateOf(settings.general.currency) }
    var numberFormat by remember { mutableStateOf(settings.general.numberFormat) }

    LaunchedEffect(settings.general) {
        dateFormat = settings.general.dateFormat
        timeFormat = settings.general.timeFormat
        language = settings.general.language
        currency = settings.general.currency
        numberFormat = settings.general.numberFormat
    }

    val updateSettings = {
        val updated = settings.copy(
            general = settings.general.copy(
                dateFormat = dateFormat,
                timeFormat = timeFormat,
                language = language,
                currency = currency,
                numberFormat = numberFormat
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
                    value = dateFormat,
                    onValueChange = { dateFormat = it; updateSettings() },
                    label = { Text("Date Format") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = timeFormat,
                    onValueChange = { timeFormat = it; updateSettings() },
                    label = { Text("Time Format") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = language,
                    onValueChange = { language = it; updateSettings() },
                    label = { Text("Language") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = currency,
                    onValueChange = { currency = it; updateSettings() },
                    label = { Text("Currency") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = numberFormat,
                onValueChange = { numberFormat = it; updateSettings() },
                label = { Text("Number Format") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}
