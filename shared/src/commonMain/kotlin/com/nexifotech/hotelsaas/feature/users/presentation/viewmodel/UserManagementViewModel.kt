package com.nexifotech.hotelsaas.feature.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.users.data.repository.DummyUserRepository
import com.nexifotech.hotelsaas.feature.users.domain.usecase.GetUserMetricsUseCase
import com.nexifotech.hotelsaas.feature.users.domain.usecase.GetUsersUseCase
import com.nexifotech.hotelsaas.feature.users.presentation.state.UserManagementState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserManagementViewModel : ViewModel() {

    private val repository = DummyUserRepository()
    private val getUsersUseCase = GetUsersUseCase(repository)
    private val getUserMetricsUseCase = GetUserMetricsUseCase(repository)

    private val _uiState = MutableStateFlow(UserManagementState())
    val uiState: StateFlow<UserManagementState> = _uiState.asStateFlow()

    init {
        loadMetrics()
        loadUsers()
    }

    private fun loadMetrics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getUserMetricsUseCase().collectLatest { metrics ->
                _uiState.update { 
                    it.copy(
                        metrics = metrics,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val state = _uiState.value
            getUsersUseCase(
                searchQuery = state.searchQuery,
                roleFilter = state.selectedRoleFilter,
                statusFilter = state.selectedStatusFilter
            ).collectLatest { users ->
                _uiState.update {
                    it.copy(
                        users = users,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        loadUsers()
    }

    fun onRoleFilterChange(role: String) {
        _uiState.update { it.copy(selectedRoleFilter = role) }
        loadUsers()
    }

    fun onStatusFilterChange(status: String) {
        _uiState.update { it.copy(selectedStatusFilter = status) }
        loadUsers()
    }
}
