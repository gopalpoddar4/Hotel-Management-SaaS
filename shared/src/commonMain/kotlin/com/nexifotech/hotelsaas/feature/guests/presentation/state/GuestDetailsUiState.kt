package com.nexifotech.hotelsaas.feature.guests.presentation.state

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest

data class GuestDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val guest: Guest? = null
)
