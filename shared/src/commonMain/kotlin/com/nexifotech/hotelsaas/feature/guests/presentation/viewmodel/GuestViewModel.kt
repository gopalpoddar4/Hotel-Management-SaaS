package com.nexifotech.hotelsaas.feature.guests.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.guests.data.datasource.FakeGuestDataSource
import com.nexifotech.hotelsaas.feature.guests.data.repository.GuestRepositoryImpl
import com.nexifotech.hotelsaas.feature.guests.domain.repository.GuestRepository
import com.nexifotech.hotelsaas.feature.guests.domain.usecase.GetGuestSummaryUseCase
import com.nexifotech.hotelsaas.feature.guests.domain.usecase.GetGuestsUseCase
import com.nexifotech.hotelsaas.feature.guests.presentation.event.GuestEvent
import com.nexifotech.hotelsaas.feature.guests.presentation.state.GuestUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestViewModel(
    private val repository: GuestRepository = GuestRepositoryImpl(FakeGuestDataSource()),
    private val getGuestsUseCase: GetGuestsUseCase = GetGuestsUseCase(repository),
    private val getGuestSummaryUseCase: GetGuestSummaryUseCase = GetGuestSummaryUseCase(repository)
) : ViewModel() {

    private val _uiState = MutableStateFlow(GuestUiState())
    val uiState: StateFlow<GuestUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: GuestEvent) {
        when (event) {
            is GuestEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                performSearch()
            }
            is GuestEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                performSearch()
            }
            is GuestEvent.OnGuestClicked -> {
                // Handled via navigation
            }
            GuestEvent.OnRefresh -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val metrics = getGuestSummaryUseCase()
                val guests = getGuestsUseCase()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        metrics = metrics,
                        allGuests = guests,
                        filteredGuests = guests
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
                val filtered = repository.searchGuests(currentState.searchQuery, currentState.selectedFilter)
                _uiState.update { it.copy(filteredGuests = filtered) }
            } catch (e: Exception) {
                // Search error handling
            }
        }
    }
}
