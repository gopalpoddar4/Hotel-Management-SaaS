package com.nexifotech.hotelsaas.feature.auth.domain.repository

import com.nexifotech.hotelsaas.feature.auth.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(username: String, password: String): AuthResult
}
