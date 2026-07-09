package com.nexifotech.hotelsaas.feature.billing.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.nexifotech.hotelsaas.feature.billing.presentation.components.*
import com.nexifotech.hotelsaas.feature.billing.presentation.event.BillingEvent
import com.nexifotech.hotelsaas.feature.billing.presentation.viewmodel.BillingViewModel

@Composable
fun BillingScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: BillingViewModel = viewModel { BillingViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = rememberWindowSizeClass()

    if (uiState.isLoading && uiState.allInvoices.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                        )
                    )
                )
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "Billing Management",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                )
            }

            item {
                uiState.summary?.let { summary ->
                    BillingSummarySection(
                        summary = summary,
                        windowSizeClass = windowSizeClass
                    )
                }
            }

            item {
                if (windowSizeClass == WindowSizeClass.Expanded || windowSizeClass == WindowSizeClass.Medium) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(2f),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            BillingSearchAndFilter(
                                searchQuery = uiState.searchQuery,
                                onSearchQueryChange = { viewModel.onEvent(BillingEvent.OnSearchQueryChanged(it)) },
                                filters = uiState.filters,
                                selectedFilter = uiState.selectedFilter,
                                onFilterSelected = { viewModel.onEvent(BillingEvent.OnFilterSelected(it)) }
                            )
                            
                            // Invoices List
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                uiState.filteredInvoices.forEach { invoice ->
                                    InvoiceCard(
                                        invoice = invoice,
                                        onClick = { 
                                            viewModel.onEvent(BillingEvent.OnInvoiceClicked(invoice.id))
                                            onNavigateToDetails(invoice.id)
                                        }
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            BillingActionButtons()
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        BillingActionButtons()
                        
                        BillingSearchAndFilter(
                            searchQuery = uiState.searchQuery,
                            onSearchQueryChange = { viewModel.onEvent(BillingEvent.OnSearchQueryChanged(it)) },
                            filters = uiState.filters,
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelected = { viewModel.onEvent(BillingEvent.OnFilterSelected(it)) }
                        )
                        
                        // Invoices List
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            uiState.filteredInvoices.forEach { invoice ->
                                InvoiceCard(
                                    invoice = invoice,
                                    onClick = { 
                                        viewModel.onEvent(BillingEvent.OnInvoiceClicked(invoice.id))
                                        onNavigateToDetails(invoice.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
