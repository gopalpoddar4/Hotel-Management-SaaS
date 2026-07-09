package com.nexifotech.hotelsaas.feature.billing.presentation.event

import com.nexifotech.hotelsaas.feature.billing.domain.model.PaymentStatus

sealed class BillingEvent {
    data class OnSearchQueryChanged(val query: String) : BillingEvent()
    data class OnFilterSelected(val filter: PaymentStatus?) : BillingEvent()
    data class OnInvoiceClicked(val invoiceId: String) : BillingEvent()
    object LoadInvoices : BillingEvent()
}
