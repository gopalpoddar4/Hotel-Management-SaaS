package com.nexifotech.hotelsaas.feature.rooms.domain.usecase

import com.nexifotech.hotelsaas.feature.rooms.domain.model.Room
import com.nexifotech.hotelsaas.feature.rooms.domain.repository.RoomRepository

class GetRoomDetailsUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(roomId: String): Room? {
        return repository.getRoomById(roomId)
    }
}
