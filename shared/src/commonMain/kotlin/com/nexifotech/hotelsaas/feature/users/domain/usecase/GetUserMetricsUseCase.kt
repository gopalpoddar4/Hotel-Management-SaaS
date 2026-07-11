package com.nexifotech.hotelsaas.feature.users.domain.usecase

import com.nexifotech.hotelsaas.feature.users.domain.model.UserMetrics
import com.nexifotech.hotelsaas.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserMetricsUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<UserMetrics> {
        return repository.getUserMetrics()
    }
}
