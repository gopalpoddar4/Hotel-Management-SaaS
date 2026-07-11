package com.nexifotech.hotelsaas.feature.auth.domain.usecase

import com.nexifotech.hotelsaas.feature.auth.domain.model.AuthResult
import com.nexifotech.hotelsaas.feature.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): AuthResult {
        if (username.isBlank()) {
            return AuthResult.Error("Username is required")
        }
        if (password.isBlank()) {
            return AuthResult.Error("Password is required")
        }
        return authRepository.login(username, password)
    }
}
