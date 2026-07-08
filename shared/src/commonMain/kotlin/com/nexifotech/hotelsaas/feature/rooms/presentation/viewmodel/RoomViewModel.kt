package com.nexifotech.hotelsaas.feature.rooms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.rooms.data.datasource.FakeRoomDataSource
import com.nexifotech.hotelsaas.feature.rooms.data.repository.RoomRepositoryImpl
import com.nexifotech.hotelsaas.feature.rooms.domain.repository.RoomRepository
import com.nexifotech.hotelsaas.feature.rooms.presentation.event.RoomEvent
import com.nexifotech.hotelsaas.feature.rooms.presentation.state.RoomUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomViewModel(
    private val repository: RoomRepository = RoomRepositoryImpl(FakeRoomDataSource())
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoomUiState())
    val uiState: StateFlow<RoomUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: RoomEvent) {
        when (event) {
            is RoomEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                performSearch()
            }
            is RoomEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                performSearch()
            }
            is RoomEvent.OnRoomClicked -> {
                // Handled via navigation in screen
            }
            RoomEvent.OnRefresh -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val metrics = repository.getDashboardSummary()
                val rooms = repository.getRooms()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        metrics = metrics,
                        allRooms = rooms,
                        filteredRooms = rooms
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
                val filtered = repository.searchRooms(currentState.searchQuery, currentState.selectedFilter)
                _uiState.update { it.copy(filteredRooms = filtered) }
            } catch (e: Exception) {
                // Handle search error if needed
            }
        }
    }
}
