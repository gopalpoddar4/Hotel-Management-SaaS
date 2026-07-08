package com.nexifotech.hotelsaas.feature.rooms.domain.model

data class Room(
    val id: String,
    val roomNumber: String,
    val roomType: RoomType,
    val floor: Int,
    val capacity: Int,
    val bedType: String,
    val pricePerNight: Double,
    val status: RoomStatus,
    val amenities: List<String>,
    val description: String,
    val currentGuestName: String? = null,
    val checkInDate: String? = null,
    val expectedCheckOutDate: String? = null,
    val housekeepingStatus: String,
    val lastCleaningDate: String,
    val lastMaintenanceDate: String
)
