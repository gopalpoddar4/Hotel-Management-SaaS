package com.nexifotech.hotelsaas.feature.guests.domain.usecase

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository

class GetGuestsUseCase(private val repository: GuestRepository) {
    suspend operator fun invoke(): List<Guest> {
        return repository.getGuests()
    }
}
