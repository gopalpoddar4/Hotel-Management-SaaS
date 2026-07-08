package com.nexifotech.hotelsaas.feature.guests.presentation.event

import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestStatus

sealed class GuestDetailsEvent {
    data class OnChangeStatus(val status: GuestStatus) : GuestDetailsEvent()
    data class OnUpdateNotes(val notes: String) : GuestDetailsEvent()
    data object OnRefresh : GuestDetailsEvent()
}
