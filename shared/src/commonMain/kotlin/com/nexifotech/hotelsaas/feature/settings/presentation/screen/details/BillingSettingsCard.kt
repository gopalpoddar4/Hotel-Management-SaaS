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
fun BillingSettingsCard(
    settings: AppSettings,
    onEvent: (SettingsEvent) -> Unit
) {
    var taxConfiguration by remember { mutableStateOf(settings.billing.taxConfiguration) }
    var gstNumber by remember { mutableStateOf(settings.billing.gstNumber) }
    var invoicePrefix by remember { mutableStateOf(settings.billing.invoicePrefix) }
    var invoiceFooter by remember { mutableStateOf(settings.billing.invoiceFooter) }
    var paymentDefaults by remember { mutableStateOf(settings.billing.paymentDefaults) }

    LaunchedEffect(settings.billing) {
        taxConfiguration = settings.billing.taxConfiguration
        gstNumber = settings.billing.gstNumber
        invoicePrefix = settings.billing.invoicePrefix
        invoiceFooter = settings.billing.invoiceFooter
        paymentDefaults = settings.billing.paymentDefaults
    }

    val updateSettings = {
        val updated = settings.copy(
            billing = settings.billing.copy(
                taxConfiguration = taxConfiguration,
                gstNumber = gstNumber,
                invoicePrefix = invoicePrefix,
                invoiceFooter = invoiceFooter,
                paymentDefaults = paymentDefaults
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
                    value = taxConfiguration,
                    onValueChange = { taxConfiguration = it; updateSettings() },
                    label = { Text("Tax Configuration") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = gstNumber,
                    onValueChange = { gstNumber = it; updateSettings() },
                    label = { Text("GST Number") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = invoicePrefix,
                    onValueChange = { invoicePrefix = it; updateSettings() },
                    label = { Text("Invoice Prefix") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = paymentDefaults,
                    onValueChange = { paymentDefaults = it; updateSettings() },
                    label = { Text("Payment Defaults") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = invoiceFooter,
                onValueChange = { invoiceFooter = it; updateSettings() },
                label = { Text("Invoice Footer") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
        }
    }
}
