package com.nexifotech.hotelsaas.feature.auth.presentation.state

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val errorMessage: String? = null
)
