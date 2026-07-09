package com.nexifotech.hotelsaas.feature.billing.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexifotech.hotelsaas.core.ui.adaptive.WindowSizeClass
import com.nexifotech.hotelsaas.core.ui.adaptive.rememberWindowSizeClass
import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.billing.presentation.components.InvoiceDetailRow
import com.nexifotech.hotelsaas.feature.billing.presentation.components.InvoiceInfoCard
import com.nexifotech.hotelsaas.feature.billing.presentation.components.PaymentStatusChip
import com.nexifotech.hotelsaas.feature.billing.presentation.event.BillingDetailsEvent
import com.nexifotech.hotelsaas.feature.billing.presentation.viewmodel.BillingDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillingDetailsScreen(
    invoiceId: String,
    onBackClick: () -> Unit,
    viewModel: BillingDetailsViewModel = viewModel { BillingDetailsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    LaunchedEffect(invoiceId) {
        viewModel.onEvent(BillingDetailsEvent.LoadInvoice(invoiceId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Invoice Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            uiState.invoice?.let { invoice ->
                if (invoice.paymentStatus != PaymentStatus.PAID) {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Remaining",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$${invoice.remainingAmount}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Button(
                                onClick = { viewModel.onEvent(BillingDetailsEvent.MarkAsPaid) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Mark as Paid")
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text(text = uiState.error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
            }
        } else {
            uiState.invoice?.let { invoice ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                                )
                            )
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = invoice.invoiceNumber,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            PaymentStatusChip(status = invoice.paymentStatus)
                        }
                    }

                    item {
                        InvoiceInfoCard(title = "Guest & Booking Information") {
                            InvoiceDetailRow("Guest Name", invoice.guestName)
                            InvoiceDetailRow("Room", invoice.roomNumber)
                            InvoiceDetailRow("Booking ID", invoice.bookingId)
                            InvoiceDetailRow("Check-in", invoice.checkInDate)
                            InvoiceDetailRow("Check-out", invoice.checkOutDate)
                            InvoiceDetailRow("Duration", "${invoice.stayDurationDays} Nights")
                        }
                    }

                    item {
                        InvoiceInfoCard(title = "Charges Breakdown") {
                            InvoiceDetailRow("Room Charges", "$${invoice.roomCharges}")
                            InvoiceDetailRow("Extra Charges", "$${invoice.extraCharges}")
                            InvoiceDetailRow("Restaurant", "$${invoice.restaurantCharges}")
                            InvoiceDetailRow("Laundry", "$${invoice.laundryCharges}")
                            if (invoice.discount > 0) {
                                InvoiceDetailRow("Discount", "-$${invoice.discount}", valueColor = MaterialTheme.colorScheme.primary)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            InvoiceDetailRow("Subtotal", "$${invoice.totalAmount - invoice.taxAmount}")
                            InvoiceDetailRow("Tax (${invoice.taxPercentage}%)", "$${invoice.taxAmount}")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            InvoiceDetailRow("Grand Total", "$${invoice.totalAmount}", isBold = true)
                        }
                    }

                    item {
                        InvoiceInfoCard(title = "Payment Details") {
                            InvoiceDetailRow("Amount Paid", "$${invoice.paidAmount}")
                            InvoiceDetailRow("Remaining", "$${invoice.remainingAmount}", isBold = true, valueColor = if (invoice.remainingAmount > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface)
                            InvoiceDetailRow("Payment Method", invoice.paymentMethod)
                            if (invoice.paymentDate != null) {
                                InvoiceDetailRow("Payment Date", invoice.paymentDate)
                            }
                        }
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
