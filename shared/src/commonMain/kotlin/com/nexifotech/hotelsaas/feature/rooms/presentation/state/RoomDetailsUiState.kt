package com.nexifotech.hotelsaas.feature.rooms.presentation.state

import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room

data class RoomDetailsUiState(
    val isLoading: Boolean = false,
    val room: Room? = null,
    val error: String? = null
)
