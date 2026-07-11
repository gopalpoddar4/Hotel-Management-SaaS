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
fun HotelSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var hotelName by remember { mutableStateOf(settings.hotelProfile.hotelName) }
    var address by remember { mutableStateOf(settings.hotelProfile.address) }
    var email by remember { mutableStateOf(settings.hotelProfile.contactEmail) }
    var phone by remember { mutableStateOf(settings.hotelProfile.contactPhone) }
    var timeZone by remember { mutableStateOf(settings.hotelProfile.timeZone) }
    var businessHours by remember { mutableStateOf(settings.hotelProfile.businessHours) }

    // Update state when settings change remotely
    LaunchedEffect(settings.hotelProfile) {
        hotelName = settings.hotelProfile.hotelName
        address = settings.hotelProfile.address
        email = settings.hotelProfile.contactEmail
        phone = settings.hotelProfile.contactPhone
        timeZone = settings.hotelProfile.timeZone
        businessHours = settings.hotelProfile.businessHours
    }

    val updateSettings = {
        val updated = settings.copy(
            hotelProfile = settings.hotelProfile.copy(
                hotelName = hotelName,
                address = address,
                contactEmail = email,
                contactPhone = phone,
                timeZone = timeZone,
                businessHours = businessHours
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
                value = hotelName,
                onValueChange = { hotelName = it; updateSettings() },
                label = { Text("Hotel Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it; updateSettings() },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; updateSettings() },
                    label = { Text("Contact Email") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it; updateSettings() },
                    label = { Text("Contact Phone") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = timeZone,
                    onValueChange = { timeZone = it; updateSettings() },
                    label = { Text("Time Zone") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = businessHours,
                    onValueChange = { businessHours = it; updateSettings() },
                    label = { Text("Business Hours") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }
    }
}
