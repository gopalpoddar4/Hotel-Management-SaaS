package com.nexifotech.hotelsaas.feature.housekeeping.domain.model

data class HousekeepingSummary(
    val totalRooms: Int,
    val cleanRooms: Int,
    val dirtyRooms: Int,
    val roomsInCleaning: Int,
    val maintenanceRequests: Int,
    val completedToday: Int,
    val pendingTasks: Int
)
