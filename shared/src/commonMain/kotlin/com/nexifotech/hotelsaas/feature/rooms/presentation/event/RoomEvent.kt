package com.nexifotech.hotelsaas.feature.rooms.presentation.event

import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus

sealed class RoomEvent {
    data class OnSearchQueryChanged(val query: String) : RoomEvent()
    data class OnFilterSelected(val filter: RoomStatus?) : RoomEvent()
    data class OnRoomClicked(val roomId: String) : RoomEvent()
    data object OnRefresh : RoomEvent()
}
