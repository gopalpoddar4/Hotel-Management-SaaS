package com.nexifotech.hotelsaas.feature.frontoffice.presentation.event

sealed class GuestDetailsEvent {
    data class LoadGuest(val guestId: String) : GuestDetailsEvent()
    data class OnCheckInClicked(val guestId: String) : GuestDetailsEvent()
    data class OnCheckOutClicked(val guestId: String) : GuestDetailsEvent()
    data class OnCallClicked(val guestId: String) : GuestDetailsEvent()
    data class OnMessageClicked(val guestId: String) : GuestDetailsEvent()
    data class OnEditClicked(val guestId: String) : GuestDetailsEvent()
    object OnRetry : GuestDetailsEvent()
    object ClearError : GuestDetailsEvent()
}
