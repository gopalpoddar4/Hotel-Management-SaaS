package com.nexifotech.hotelsaas.feature.guests.domain.usecase

import com.nexifotech.hotelsaas.feature.guests.domain.model.Guest
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository

class GetGuestDetailsUseCase(private val repository: GuestRepository) {
    suspend operator fun invoke(id: String): Guest? {
        return repository.getGuestById(id)
    }
}
