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
fun PrinterSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var receiptPrinter by remember { mutableStateOf(settings.printer.receiptPrinter) }
    var invoicePrinter by remember { mutableStateOf(settings.printer.invoicePrinter) }
    var kitchenPrinter by remember { mutableStateOf(settings.printer.kitchenPrinter) }

    LaunchedEffect(settings.printer) {
        receiptPrinter = settings.printer.receiptPrinter
        invoicePrinter = settings.printer.invoicePrinter
        kitchenPrinter = settings.printer.kitchenPrinter
    }

    val updateSettings = {
        val updated = settings.copy(
            printer = settings.printer.copy(
                receiptPrinter = receiptPrinter,
                invoicePrinter = invoicePrinter,
                kitchenPrinter = kitchenPrinter
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
                value = receiptPrinter,
                onValueChange = { receiptPrinter = it; updateSettings() },
                label = { Text("Receipt Printer") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = invoicePrinter,
                onValueChange = { invoicePrinter = it; updateSettings() },
                label = { Text("Invoice Printer") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = kitchenPrinter,
                onValueChange = { kitchenPrinter = it; updateSettings() },
                label = { Text("Kitchen Printer") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}
