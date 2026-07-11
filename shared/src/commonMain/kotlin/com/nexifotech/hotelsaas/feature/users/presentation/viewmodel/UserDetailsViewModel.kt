package com.nexifotech.hotelsaas.feature.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.users.data.repository.DummyUserRepository
import com.nexifotech.hotelsaas.feature.users.domain.usecase.GetUserByIdUseCase
import com.nexifotech.hotelsaas.feature.users.presentation.state.UserDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailsViewModel : ViewModel() {
    
    private val repository = DummyUserRepository()
    private val getUserByIdUseCase = GetUserByIdUseCase(repository)

    private val _uiState = MutableStateFlow(UserDetailsState())
    val uiState: StateFlow<UserDetailsState> = _uiState.asStateFlow()

    fun loadUser(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getUserByIdUseCase(userId).collectLatest { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            user = user,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "User not found"
                        )
                    }
                }
            }
        }
    }
}
