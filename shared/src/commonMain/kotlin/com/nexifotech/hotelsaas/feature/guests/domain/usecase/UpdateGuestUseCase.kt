package com.nexifotech.hotelsaas.feature.guests.domain.usecase

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository

class UpdateGuestUseCase(private val repository: GuestRepository) {
    suspend operator fun invoke(guest: Guest) {
        repository.updateGuest(guest)
    }
}
