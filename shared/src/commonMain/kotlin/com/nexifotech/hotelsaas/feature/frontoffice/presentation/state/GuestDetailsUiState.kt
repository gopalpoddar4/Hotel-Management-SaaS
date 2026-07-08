package com.nexifotech.hotelsaas.feature.frontoffice.presentation.state

import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest

data class GuestDetailsUiState(
    val isLoading: Boolean = false,
    val guest: Guest? = null,
    val error: String? = null
)
