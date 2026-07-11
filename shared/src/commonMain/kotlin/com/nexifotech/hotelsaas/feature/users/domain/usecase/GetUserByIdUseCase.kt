package com.nexifotech.hotelsaas.feature.users.domain.usecase

import com.nexifotech.hotelsaas.feature.users.domain.model.User
import com.nexifotech.hotelsaas.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String): Flow<User?> {
        return repository.getUserById(userId)
    }
}
