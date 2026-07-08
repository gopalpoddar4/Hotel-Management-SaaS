package com.nexifotech.hotelsaas.feature.frontoffice.presentation.event

import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest

sealed class FrontOfficeEvent {
    data class OnSearchQueryChanged(val query: String) : FrontOfficeEvent()
    data class OnFilterSelected(val filter: String) : FrontOfficeEvent()
    data class OnGuestClicked(val guest: Guest) : FrontOfficeEvent()
    object OnCheckInGuestClicked : FrontOfficeEvent()
    object OnCheckOutGuestClicked : FrontOfficeEvent()
    object OnWalkInGuestClicked : FrontOfficeEvent()
    object OnNewReservationClicked : FrontOfficeEvent()
    object OnRefresh : FrontOfficeEvent()
}
