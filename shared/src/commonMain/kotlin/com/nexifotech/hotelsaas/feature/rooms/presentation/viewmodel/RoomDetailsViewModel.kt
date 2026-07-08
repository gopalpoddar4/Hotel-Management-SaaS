package com.nexifotech.hotelsaas.feature.rooms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.rooms.data.datasource.FakeRoomDataSource
import com.nexifotech.hotelsaas.feature.rooms.data.repository.RoomRepositoryImpl
import com.nexifotech.hotelsaas.feature.rooms.domain.repository.RoomRepository
import com.nexifotech.hotelsaas.feature.rooms.presentation.event.RoomDetailsEvent
import com.nexifotech.hotelsaas.feature.rooms.presentation.state.RoomDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.nexifotech.hotelsaas.feature.rooms.domain.usecase.GetRoomDetailsUseCase

class RoomDetailsViewModel(
    private val roomId: String,
    private val repository: RoomRepository = RoomRepositoryImpl(FakeRoomDataSource()),
    private val getRoomDetailsUseCase: GetRoomDetailsUseCase = GetRoomDetailsUseCase(repository)
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoomDetailsUiState())
    val uiState: StateFlow<RoomDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRoom()
    }

    fun onEvent(event: RoomDetailsEvent) {
        when (event) {
            is RoomDetailsEvent.OnAssignGuest -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    repository.assignRoom(roomId, event.guestName)
                    loadRoom()
                }
            }
            is RoomDetailsEvent.OnChangeStatus -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    repository.updateRoomStatus(roomId, event.status)
                    loadRoom()
                }
            }
            RoomDetailsEvent.OnRefresh -> {
                loadRoom()
            }
            RoomDetailsEvent.OnReleaseRoom -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    repository.releaseRoom(roomId)
                    loadRoom()
                }
            }
        }
    }

    private fun loadRoom() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val room = getRoomDetailsUseCase(roomId)
                if (room != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            room = room
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Room not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred while loading room details"
                    )
                }
            }
        }
    }
}
