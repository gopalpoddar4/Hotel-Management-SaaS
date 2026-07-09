package com.nexifotech.hotelsaas.feature.billing.presentation.event

sealed class BillingDetailsEvent {
    data class LoadInvoice(val id: String) : BillingDetailsEvent()
    object MarkAsPaid : BillingDetailsEvent()
    object PrintInvoice : BillingDetailsEvent()
    object SendInvoice : BillingDetailsEvent()
}
