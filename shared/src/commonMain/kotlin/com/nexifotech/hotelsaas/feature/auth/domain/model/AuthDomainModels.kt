package com.nexifotech.hotelsaas.feature.auth.domain.model

sealed interface AuthResult {
    data class Success(val username: String) : AuthResult
    data class Error(val message: String) : AuthResult
}
