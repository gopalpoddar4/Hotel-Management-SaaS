package com.nexifotech.hotelsaas.feature.rooms.presentation.state

import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomSummaryMetrics

data class RoomUiState(
    val isLoading: Boolean = false,
    val allRooms: List<Room> = emptyList(),
    val filteredRooms: List<Room> = emptyList(),
    val metrics: RoomSummaryMetrics = RoomSummaryMetrics(),
    val searchQuery: String = "",
    val filters: List<RoomStatus> = RoomStatus.entries.toList(),
    val selectedFilter: RoomStatus? = null,
    val error: String? = null
)
