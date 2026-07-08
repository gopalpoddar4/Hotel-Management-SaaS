package com.nexifotech.hotelsaas.feature.guests.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.guests.data.datasource.FakeGuestDataSource
import com.nexifotech.hotelsaas.feature.guests.data.repository.GuestRepositoryImpl
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository
import com.nexifotech.hotelsaas.feature.guests.domain.usecase.GetGuestDetailsUseCase
import com.nexifotech.hotelsaas.feature.guests.domain.usecase.UpdateGuestUseCase
import com.nexifotech.hotelsaas.feature.guests.presentation.event.GuestDetailsEvent
import com.nexifotech.hotelsaas.feature.guests.presentation.state.GuestDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestDetailsViewModel(
    private val guestId: String,
    repository: GuestRepository = GuestRepositoryImpl(FakeGuestDataSource()),
    private val getGuestDetailsUseCase: GetGuestDetailsUseCase = GetGuestDetailsUseCase(repository),
    private val updateGuestUseCase: UpdateGuestUseCase = UpdateGuestUseCase(repository)
) : ViewModel() {

    private val _uiState = MutableStateFlow(GuestDetailsUiState())
    val uiState: StateFlow<GuestDetailsUiState> = _uiState.asStateFlow()

    init {
        loadGuest()
    }

    fun onEvent(event: GuestDetailsEvent) {
        when (event) {
            is GuestDetailsEvent.OnChangeStatus -> {
                val currentGuest = _uiState.value.guest
                if (currentGuest != null) {
                    viewModelScope.launch {
                        _uiState.update { it.copy(isLoading = true) }
                        updateGuestUseCase(currentGuest.copy(status = event.status))
                        loadGuest()
                    }
                }
            }
            is GuestDetailsEvent.OnUpdateNotes -> {
                val currentGuest = _uiState.value.guest
                if (currentGuest != null) {
                    viewModelScope.launch {
                        _uiState.update { it.copy(isLoading = true) }
                        updateGuestUseCase(currentGuest.copy(notes = event.notes))
                        loadGuest()
                    }
                }
            }
            GuestDetailsEvent.OnRefresh -> {
                loadGuest()
            }
        }
    }

    private fun loadGuest() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val guest = getGuestDetailsUseCase(guestId)
                if (guest != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            guest = guest
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Guest not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred while loading guest details"
                    )
                }
            }
        }
    }
}
