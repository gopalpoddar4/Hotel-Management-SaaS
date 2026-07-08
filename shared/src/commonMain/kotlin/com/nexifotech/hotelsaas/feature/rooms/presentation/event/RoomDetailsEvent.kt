package com.nexifotech.hotelsaas.feature.rooms.presentation.event

import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus

sealed class RoomDetailsEvent {
    data class OnAssignGuest(val guestName: String) : RoomDetailsEvent()
    data object OnReleaseRoom : RoomDetailsEvent()
    data class OnChangeStatus(val status: RoomStatus) : RoomDetailsEvent()
    data object OnRefresh : RoomDetailsEvent()
}
