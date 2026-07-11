package com.nexifotech.hotelsaas.feature.users.domain.repository

import com.nexifotech.hotelsaas.feature.users.domain.model.User
import com.nexifotech.hotelsaas.feature.users.domain.model.UserMetrics
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun getUserById(userId: String): Flow<User?>
    fun getUserMetrics(): Flow<UserMetrics>
}
