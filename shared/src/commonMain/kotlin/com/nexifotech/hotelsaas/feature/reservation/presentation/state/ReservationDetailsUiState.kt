package com.nexifotech.hotelsaas.feature.reservation.presentation.state

import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation

data class ReservationDetailsUiState(
    val isLoading: Boolean = false,
    val reservation: Reservation? = null,
    val error: String? = null
)
