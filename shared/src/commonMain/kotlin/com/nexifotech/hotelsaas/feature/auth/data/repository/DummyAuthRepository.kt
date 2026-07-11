package com.nexifotech.hotelsaas.feature.auth.data.repository

import com.nexifotech.hotelsaas.feature.auth.domain.model.AuthResult
import com.nexifotech.hotelsaas.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class DummyAuthRepository : AuthRepository {
    override suspend fun login(username: String, password: String): AuthResult {
        // Simulate network delay
        delay(1500)
        
        return if (username == "admin" && password == "admin123") {
            AuthResult.Success(username = username)
        } else {
            AuthResult.Error("Invalid username or password")
        }
    }
}
