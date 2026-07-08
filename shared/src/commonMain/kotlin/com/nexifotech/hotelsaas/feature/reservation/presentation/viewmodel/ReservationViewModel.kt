package com.nexifotech.hotelsaas.feature.reservation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.reservation.data.datasource.FakeReservationDataSource
import com.nexifotech.hotelsaas.feature.reservation.data.repository.ReservationRepositoryImpl
import com.nexifotech.hotelsaas.feature.reservation.domain.repository.ReservationRepository
import com.nexifotech.hotelsaas.feature.reservation.presentation.event.ReservationEvent
import com.nexifotech.hotelsaas.feature.reservation.presentation.state.ReservationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val repository: ReservationRepository = ReservationRepositoryImpl(FakeReservationDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: ReservationEvent) {
        when (event) {
            is ReservationEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                performSearch()
            }
            is ReservationEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                performSearch()
            }
            is ReservationEvent.OnReservationClicked -> {
                // Handled via navigation in screen
            }
            ReservationEvent.OnNewReservationClicked -> {
                // Mock behavior
            }
            ReservationEvent.OnRefresh -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val metrics = repository.getDashboardSummary()
                val reservations = repository.getReservations()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        metrics = metrics,
                        allReservations = reservations,
                        filteredReservations = reservations
                    )
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

    private fun performSearch() {
        viewModelScope.launch {
            val currentState = _uiState.value
            try {
                val filtered = repository.searchReservations(currentState.searchQuery, currentState.selectedFilter)
                _uiState.update { it.copy(filteredReservations = filtered) }
            } catch (e: Exception) {
                // Handle search error if needed
            }
        }
    }
}
