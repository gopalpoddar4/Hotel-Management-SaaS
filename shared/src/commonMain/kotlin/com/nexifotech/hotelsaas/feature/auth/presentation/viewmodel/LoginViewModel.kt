package com.nexifotech.hotelsaas.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.core.navigation.AppNavigator
import com.nexifotech.hotelsaas.core.navigation.AppRoutes
import com.nexifotech.hotelsaas.feature.auth.domain.model.AuthResult
import com.nexifotech.hotelsaas.feature.auth.domain.usecase.LoginUseCase
import com.nexifotech.hotelsaas.feature.auth.presentation.event.LoginEvent
import com.nexifotech.hotelsaas.feature.auth.presentation.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                _uiState.update { it.copy(username = event.username, errorMessage = null) }
            }
            is LoginEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password, errorMessage = null) }
            }
            is LoginEvent.RememberMeChanged -> {
                _uiState.update { it.copy(rememberMe = event.rememberMe) }
            }
            LoginEvent.TogglePasswordVisibility -> {
                _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            LoginEvent.LoginClicked -> {
                login()
            }
            LoginEvent.ErrorDismissed -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun login() {
        val currentState = _uiState.value
        if (currentState.username.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Username and password cannot be empty") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = loginUseCase(currentState.username, currentState.password)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    // Navigate to Main Dashboard and clear backstack of Auth
                    appNavigator.navigateAndPopUpTo(
                        route = AppRoutes.Main,
                        popUpTo = AppRoutes.Auth,
                        inclusive = true
                    )
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }
}
