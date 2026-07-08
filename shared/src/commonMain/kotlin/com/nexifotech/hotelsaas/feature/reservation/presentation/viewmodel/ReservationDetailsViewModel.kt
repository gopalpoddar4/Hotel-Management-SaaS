package com.nexifotech.hotelsaas.feature.reservation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.reservation.data.datasource.FakeReservationDataSource
import com.nexifotech.hotelsaas.feature.reservation.data.repository.ReservationRepositoryImpl
import com.nexifotech.hotelsaas.feature.reservation.domain.repository.ReservationRepository
import com.nexifotech.hotelsaas.feature.reservation.presentation.event.ReservationDetailsEvent
import com.nexifotech.hotelsaas.feature.reservation.presentation.state.ReservationDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReservationDetailsViewModel(
    private val repository: ReservationRepository = ReservationRepositoryImpl(FakeReservationDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservationDetailsUiState())
    val uiState: StateFlow<ReservationDetailsUiState> = _uiState.asStateFlow()

    private var currentId: String? = null

    fun onEvent(event: ReservationDetailsEvent) {
        when (event) {
            is ReservationDetailsEvent.LoadReservation -> {
                currentId = event.id
                loadReservation(event.id)
            }
            ReservationDetailsEvent.OnRetry -> {
                currentId?.let { loadReservation(it) }
            }
            is ReservationDetailsEvent.OnCancelReservationClicked -> {
                cancelReservation(event.id)
            }
            is ReservationDetailsEvent.OnEditClicked -> {
                // Mock behavior
            }
        }
    }

    private fun loadReservation(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val reservation = repository.getReservationById(id)
                if (reservation != null) {
                    _uiState.update { it.copy(isLoading = false, reservation = reservation) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Reservation not found") }
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

    private fun cancelReservation(id: String) {
        viewModelScope.launch {
            try {
                val success = repository.cancelReservation(id)
                if (success) {
                    loadReservation(id)
                } else {
                    _uiState.update { it.copy(error = "Failed to cancel reservation") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Failed to cancel reservation") }
            }
        }
    }
}
