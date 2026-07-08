package com.nexifotech.hotelsaas.feature.frontoffice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.frontoffice.data.datasource.FakeFrontOfficeDataSource
import com.nexifotech.hotelsaas.feature.frontoffice.data.repository.FrontOfficeRepositoryImpl
import com.nexifotech.hotelsaas.feature.frontoffice.domain.model.Guest
import com.nexifotech.hotelsaas.feature.frontoffice.domain.repository.FrontOfficeRepository
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.event.FrontOfficeEvent
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.state.FrontOfficeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FrontOfficeViewModel(
    private val repository: FrontOfficeRepository = FrontOfficeRepositoryImpl(FakeFrontOfficeDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(FrontOfficeUiState())
    val uiState: StateFlow<FrontOfficeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: FrontOfficeEvent) {
        when (event) {
            is FrontOfficeEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                performSearch()
            }
            is FrontOfficeEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                performSearch()
            }
            is FrontOfficeEvent.OnGuestClicked -> {
                // Handle guest click
            }
            FrontOfficeEvent.OnCheckInGuestClicked -> {
                // Handle check in click
            }
            FrontOfficeEvent.OnCheckOutGuestClicked -> {
                // Handle check out click
            }
            FrontOfficeEvent.OnNewReservationClicked -> {
                // Handle new reservation click
            }
            FrontOfficeEvent.OnWalkInGuestClicked -> {
                // Handle walk in click
            }
            FrontOfficeEvent.OnRefresh -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val metrics = repository.getDashboardSummary()
                val guests = repository.getGuests()
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
                // Handle search error if needed
            }
        }
    }
}
