package com.nexifotech.hotelsaas.feature.frontoffice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.frontoffice.data.datasource.FakeFrontOfficeDataSource
import com.nexifotech.hotelsaas.feature.frontoffice.data.repository.FrontOfficeRepositoryImpl
import com.nexifotech.hotelsaas.feature.frontoffice.domain.repository.FrontOfficeRepository
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.event.GuestDetailsEvent
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.state.GuestDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestDetailsViewModel(
    private val repository: FrontOfficeRepository = FrontOfficeRepositoryImpl(FakeFrontOfficeDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(GuestDetailsUiState())
    val uiState: StateFlow<GuestDetailsUiState> = _uiState.asStateFlow()

    private var currentGuestId: String? = null

    fun onEvent(event: GuestDetailsEvent) {
        when (event) {
            is GuestDetailsEvent.LoadGuest -> {
                currentGuestId = event.guestId
                loadGuest(event.guestId)
            }
            GuestDetailsEvent.OnRetry -> {
                currentGuestId?.let { loadGuest(it) }
            }
            GuestDetailsEvent.ClearError -> {
                _uiState.update { it.copy(error = null) }
            }
            is GuestDetailsEvent.OnCheckInClicked -> performAction { repository.checkInGuest(event.guestId) }
            is GuestDetailsEvent.OnCheckOutClicked -> performAction { repository.checkOutGuest(event.guestId) }
            is GuestDetailsEvent.OnCallClicked -> {
                // Mock call logic
            }
            is GuestDetailsEvent.OnEditClicked -> {
                // Mock edit logic
            }
            is GuestDetailsEvent.OnMessageClicked -> {
                // Mock message logic
            }
        }
    }

    private fun loadGuest(guestId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val guest = repository.getGuestById(guestId)
                if (guest != null) {
                    _uiState.update { it.copy(isLoading = false, guest = guest) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Guest not found") }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }

    private fun performAction(action: suspend () -> Boolean) {
        viewModelScope.launch {
            try {
                val success = action()
                if (success) {
                    currentGuestId?.let { loadGuest(it) }
                } else {
                    _uiState.update { it.copy(error = "Action failed") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Action failed") }
            }
        }
    }
}
