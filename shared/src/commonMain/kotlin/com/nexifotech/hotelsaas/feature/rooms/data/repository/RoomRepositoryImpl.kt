package com.nexifotech.hotelsaas.feature.rooms.data.repository

import com.nexifotech.hotelsaas.feature.rooms.data.datasource.FakeRoomDataSource
import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomSummaryMetrics
import com.nexifotech.hotelsaas.feature.rooms.domain.repository.RoomRepository
import kotlinx.coroutines.delay

class RoomRepositoryImpl(
    private val dataSource: FakeRoomDataSource
) : RoomRepository {
    
    // Simulate network delay
    private val networkDelay = 500L

    override suspend fun getRooms(): List<Room> {
        delay(networkDelay)
        return dataSource.getAllRooms()
    }

    override suspend fun getRoomById(id: String): Room? {
        delay(networkDelay)
        return dataSource.getRoomById(id)
    }

    override suspend fun searchRooms(query: String, filter: RoomStatus?): List<Room> {
        delay(networkDelay)
        val allRooms = dataSource.getAllRooms()
        return allRooms.filter { room ->
            val matchesQuery = room.roomNumber.contains(query, ignoreCase = true) || 
                               room.roomType.name.contains(query, ignoreCase = true)
            val matchesFilter = filter == null || room.status == filter
            matchesQuery && matchesFilter
        }
    }

    override suspend fun getDashboardSummary(): RoomSummaryMetrics {
        delay(networkDelay)
        val allRooms = dataSource.getAllRooms()
        return RoomSummaryMetrics(
            totalRooms = allRooms.size,
            availableRooms = allRooms.count { it.status == RoomStatus.AVAILABLE },
            occupiedRooms = allRooms.count { it.status == RoomStatus.OCCUPIED },
            reservedRooms = allRooms.count { it.status == RoomStatus.RESERVED },
            dirtyRooms = allRooms.count { it.status == RoomStatus.CLEANING },
            maintenanceRooms = allRooms.count { it.status == RoomStatus.MAINTENANCE },
            outOfServiceRooms = allRooms.count { it.status == RoomStatus.OUT_OF_SERVICE }
        )
    }

    override suspend fun assignRoom(roomId: String, guestName: String) {
        delay(networkDelay)
        dataSource.assignRoom(roomId, guestName)
    }

    override suspend fun releaseRoom(roomId: String) {
        delay(networkDelay)
        dataSource.releaseRoom(roomId)
    }

    override suspend fun updateRoomStatus(roomId: String, status: RoomStatus) {
        delay(networkDelay)
        dataSource.updateRoomStatus(roomId, status)
    }
}
