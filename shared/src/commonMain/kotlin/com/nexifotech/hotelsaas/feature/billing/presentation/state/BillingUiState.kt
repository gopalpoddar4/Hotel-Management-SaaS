package com.nexifotech.hotelsaas.feature.billing.presentation.state

import com.nexifotech.hotelsaas.feature.billing.domain.model.Invoice
import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus
import com.nexifotech.hotelsaas.feature.billing.domain.usecase.BillingSummary

data class BillingUiState(
    val isLoading: Boolean = true,
    val allInvoices: List<Invoice> = emptyList(),
    val filteredInvoices: List<Invoice> = emptyList(),
    val searchQuery: String = "",
    val filters: List<PaymentStatus?> = listOf(
        null, // All
        PaymentStatus.PAID,
        PaymentStatus.PENDING,
        PaymentStatus.OVERDUE,
        PaymentStatus.PARTIALLY_PAID
    ),
    val selectedFilter: PaymentStatus? = null,
    val summary: BillingSummary? = null,
    val error: String? = null
)
