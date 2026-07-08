package com.nexifotech.hotelsaas.feature.reservation.presentation.event

import com.nexifotech.hotelsaas.feature.reservation.domain.model.Reservation

sealed class ReservationEvent {
    data class OnSearchQueryChanged(val query: String) : ReservationEvent()
    data class OnFilterSelected(val filter: String) : ReservationEvent()
    data class OnReservationClicked(val reservation: Reservation) : ReservationEvent()
    object OnNewReservationClicked : ReservationEvent()
    object OnRefresh : ReservationEvent()
}
