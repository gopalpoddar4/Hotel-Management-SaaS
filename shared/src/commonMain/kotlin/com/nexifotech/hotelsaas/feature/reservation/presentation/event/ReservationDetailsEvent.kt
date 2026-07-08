package com.nexifotech.hotelsaas.feature.reservation.presentation.event

sealed class ReservationDetailsEvent {
    data class LoadReservation(val id: String) : ReservationDetailsEvent()
    data class OnEditClicked(val id: String) : ReservationDetailsEvent()
    data class OnCancelReservationClicked(val id: String) : ReservationDetailsEvent()
    object OnRetry : ReservationDetailsEvent()
}
