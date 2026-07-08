package com.nexifotech.hotelsaas.feature.rooms.data.datasource

import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomStatus
import com.nexifotech.hotelsaas.feature.rooms.domain.model.RoomType

class FakeRoomDataSource {
    private val mockRooms = mutableListOf(
        Room(
            id = "R001",
            roomNumber = "101",
            roomType = RoomType.SINGLE,
            floor = 1,
            capacity = 1,
            bedType = "1 Single Bed",
            pricePerNight = 120.0,
            status = RoomStatus.AVAILABLE,
            amenities = listOf("Wi-Fi", "TV", "Air Conditioning", "Mini Fridge"),
            description = "A cozy single room perfect for solo travelers.",
            housekeepingStatus = "Clean",
            lastCleaningDate = "2023-10-25T10:00:00Z",
            lastMaintenanceDate = "2023-09-15T14:30:00Z"
        ),
        Room(
            id = "R002",
            roomNumber = "102",
            roomType = RoomType.DOUBLE,
            floor = 1,
            capacity = 2,
            bedType = "1 Double Bed",
            pricePerNight = 180.0,
            status = RoomStatus.OCCUPIED,
            amenities = listOf("Wi-Fi", "TV", "Air Conditioning", "Mini Fridge", "Work Desk"),
            description = "Spacious double room with a work desk.",
            currentGuestName = "John Doe",
            checkInDate = "2023-10-24T14:00:00Z",
            expectedCheckOutDate = "2023-10-27T11:00:00Z",
            housekeepingStatus = "Needs Cleaning",
            lastCleaningDate = "2023-10-23T11:00:00Z",
            lastMaintenanceDate = "2023-08-10T09:00:00Z"
        ),
        Room(
            id = "R003",
            roomNumber = "201",
            roomType = RoomType.SUITE,
            floor = 2,
            capacity = 4,
            bedType = "1 King Bed, 1 Sofa Bed",
            pricePerNight = 350.0,
            status = RoomStatus.RESERVED,
            amenities = listOf("Wi-Fi", "TV", "Air Conditioning", "Mini Bar", "Living Area", "Bathtub"),
            description = "Luxurious suite with a separate living area.",
            currentGuestName = "Alice Smith",
            expectedCheckOutDate = "2023-11-01T11:00:00Z",
            housekeepingStatus = "Clean",
            lastCleaningDate = "2023-10-26T09:30:00Z",
            lastMaintenanceDate = "2023-07-20T10:00:00Z"
        ),
        Room(
            id = "R004",
            roomNumber = "202",
            roomType = RoomType.DELUXE,
            floor = 2,
            capacity = 2,
            bedType = "1 King Bed",
            pricePerNight = 250.0,
            status = RoomStatus.CLEANING,
            amenities = listOf("Wi-Fi", "TV", "Air Conditioning", "Mini Bar", "Balcony"),
            description = "Deluxe room offering a beautiful balcony view.",
            housekeepingStatus = "In Progress",
            lastCleaningDate = "2023-10-26T08:00:00Z",
            lastMaintenanceDate = "2023-06-05T13:00:00Z"
        ),
        Room(
            id = "R005",
            roomNumber = "301",
            roomType = RoomType.PENTHOUSE,
            floor = 3,
            capacity = 6,
            bedType = "2 King Beds, 2 Sofa Beds",
            pricePerNight = 800.0,
            status = RoomStatus.MAINTENANCE,
            amenities = listOf("Wi-Fi", "TV", "Air Conditioning", "Private Pool", "Kitchen", "Panoramic View"),
            description = "The ultimate penthouse experience with a private pool.",
            housekeepingStatus = "Clean",
            lastCleaningDate = "2023-10-25T15:00:00Z",
            lastMaintenanceDate = "2023-10-26T09:00:00Z"
        ),
        Room(
            id = "R006",
            roomNumber = "302",
            roomType = RoomType.DOUBLE,
            floor = 3,
            capacity = 2,
            bedType = "2 Twin Beds",
            pricePerNight = 175.0,
            status = RoomStatus.OUT_OF_SERVICE,
            amenities = listOf("Wi-Fi", "TV", "Air Conditioning"),
            description = "Double room with twin beds. Currently undergoing renovation.",
            housekeepingStatus = "N/A",
            lastCleaningDate = "2023-10-01T10:00:00Z",
            lastMaintenanceDate = "2023-10-20T10:00:00Z"
        )
    )

    fun getAllRooms(): List<Room> = mockRooms.toList()

    fun getRoomById(id: String): Room? = mockRooms.find { it.id == id }

    fun updateRoomStatus(roomId: String, status: RoomStatus) {
        val index = mockRooms.indexOfFirst { it.id == roomId }
        if (index != -1) {
            mockRooms[index] = mockRooms[index].copy(status = status)
        }
    }
    
    fun assignRoom(roomId: String, guestName: String) {
        val index = mockRooms.indexOfFirst { it.id == roomId }
        if (index != -1) {
            mockRooms[index] = mockRooms[index].copy(
                status = RoomStatus.OCCUPIED,
                currentGuestName = guestName
            )
        }
    }
    
    fun releaseRoom(roomId: String) {
        val index = mockRooms.indexOfFirst { it.id == roomId }
        if (index != -1) {
            mockRooms[index] = mockRooms[index].copy(
                status = RoomStatus.CLEANING,
                currentGuestName = null,
                checkInDate = null,
                expectedCheckOutDate = null
            )
        }
    }
}
