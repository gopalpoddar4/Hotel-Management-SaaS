package com.nexifotech.hotelsaas.feature.rooms.domain.repository

import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomSummaryMetrics

interface RoomRepository {
    suspend fun getRooms(): List<Room>
    suspend fun getRoomById(id: String): Room?
    suspend fun searchRooms(query: String, filter: RoomStatus?): List<Room>
    suspend fun getDashboardSummary(): RoomSummaryMetrics
    
    // Actions
    suspend fun assignRoom(roomId: String, guestName: String)
    suspend fun releaseRoom(roomId: String)
    suspend fun updateRoomStatus(roomId: String, status: RoomStatus)
}
