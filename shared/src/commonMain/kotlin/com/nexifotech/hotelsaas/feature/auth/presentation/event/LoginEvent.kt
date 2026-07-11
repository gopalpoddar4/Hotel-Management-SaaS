package com.nexifotech.hotelsaas.feature.auth.presentation.event

sealed interface LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object TogglePasswordVisibility : LoginEvent
    data class RememberMeChanged(val rememberMe: Boolean) : LoginEvent
    data object LoginClicked : LoginEvent
    data object ErrorDismissed : LoginEvent
}
