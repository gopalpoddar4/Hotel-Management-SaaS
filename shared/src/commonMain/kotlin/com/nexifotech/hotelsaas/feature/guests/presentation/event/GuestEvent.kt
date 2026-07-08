package com.nexifotech.hotelsaas.feature.guests.presentation.event

sealed class GuestEvent {
    data class OnSearchQueryChanged(val query: String) : GuestEvent()
    data class OnFilterSelected(val filter: String) : GuestEvent()
    data class OnGuestClicked(val guestId: String) : GuestEvent()
    data object OnRefresh : GuestEvent()
}
