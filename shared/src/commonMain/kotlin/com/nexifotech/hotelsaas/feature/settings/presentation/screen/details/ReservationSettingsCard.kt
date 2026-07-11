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
fun ReservationSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var checkInTime by remember { mutableStateOf(settings.reservation.defaultCheckInTime) }
    var checkOutTime by remember { mutableStateOf(settings.reservation.defaultCheckOutTime) }
    var bookingRules by remember { mutableStateOf(settings.reservation.bookingRules) }
    var cancellationRules by remember { mutableStateOf(settings.reservation.cancellationRules) }
    var advanceBookingLimits by remember { mutableStateOf(settings.reservation.advanceBookingLimits) }

    LaunchedEffect(settings.reservation) {
        checkInTime = settings.reservation.defaultCheckInTime
        checkOutTime = settings.reservation.defaultCheckOutTime
        bookingRules = settings.reservation.bookingRules
        cancellationRules = settings.reservation.cancellationRules
        advanceBookingLimits = settings.reservation.advanceBookingLimits
    }

    val updateSettings = {
        val updated = settings.copy(
            reservation = settings.reservation.copy(
                defaultCheckInTime = checkInTime,
                defaultCheckOutTime = checkOutTime,
                bookingRules = bookingRules,
                cancellationRules = cancellationRules,
                advanceBookingLimits = advanceBookingLimits
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
                    value = checkInTime,
                    onValueChange = { checkInTime = it; updateSettings() },
                    label = { Text("Default Check-In Time") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = checkOutTime,
                    onValueChange = { checkOutTime = it; updateSettings() },
                    label = { Text("Default Check-Out Time") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = bookingRules,
                    onValueChange = { bookingRules = it; updateSettings() },
                    label = { Text("Booking Rules") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = advanceBookingLimits,
                    onValueChange = { advanceBookingLimits = it; updateSettings() },
                    label = { Text("Advance Booking Limits") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = cancellationRules,
                onValueChange = { cancellationRules = it; updateSettings() },
                label = { Text("Cancellation Rules") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
        }
    }
}
