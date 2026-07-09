package com.nexifotech.hotelsaas.feature.billing.presentation.state

import com.nexifotech.hotelsaas.feature.billing.domain.model.Invoice

data class BillingDetailsUiState(
    val isLoading: Boolean = true,
    val invoice: Invoice? = null,
    val error: String? = null
)
