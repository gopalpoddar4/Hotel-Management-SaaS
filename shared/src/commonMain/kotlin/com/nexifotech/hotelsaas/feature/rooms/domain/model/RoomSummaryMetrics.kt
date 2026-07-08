package com.nexifotech.hotelsaas.feature.rooms.domain.model

data class RoomSummaryMetrics(
    val totalRooms: Int = 0,
    val availableRooms: Int = 0,
    val occupiedRooms: Int = 0,
    val reservedRooms: Int = 0,
    val dirtyRooms: Int = 0,
    val maintenanceRooms: Int = 0,
    val outOfServiceRooms: Int = 0
)
