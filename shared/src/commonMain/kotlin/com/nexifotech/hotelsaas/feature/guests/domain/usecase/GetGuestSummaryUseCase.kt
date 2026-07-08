package com.nexifotech.hotelsaas.feature.guests.domain.usecase

import com.nexifotech.hotelsaas.feature.guests.domain.model.GuestSummaryMetrics
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository

class GetGuestSummaryUseCase(private val repository: GuestRepository) {
    suspend operator fun invoke(): GuestSummaryMetrics {
        return repository.getDashboardSummary()
    }
}
