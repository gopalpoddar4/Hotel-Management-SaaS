package com.nexifotech.hotelsaas.feature.housekeeping.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.housekeeping.data.datasource.FakeHousekeepingDataSource
import com.nexifotech.hotelsaas.feature.housekeeping.data.repository.HousekeepingRepositoryImpl
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus
import com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase.GetTaskByIdUseCase
import com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase.UpdateTaskStatusUseCase
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.event.HousekeepingDetailsEvent
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.state.HousekeepingDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HousekeepingDetailsViewModel : ViewModel() {

    // Using the same FakeDataSource approach. In a real app with DI, this would be a singleton repository injected.
    private val dataSource = FakeHousekeepingDataSource()
    private val repository = HousekeepingRepositoryImpl(dataSource)
    
    private val getTaskByIdUseCase = GetTaskByIdUseCase(repository)
    private val updateTaskStatusUseCase = UpdateTaskStatusUseCase(repository)

    private val _uiState = MutableStateFlow(HousekeepingDetailsUiState())
    val uiState: StateFlow<HousekeepingDetailsUiState> = _uiState.asStateFlow()

    private var currentTaskId: String? = null

    fun onEvent(event: HousekeepingDetailsEvent) {
        when (event) {
            is HousekeepingDetailsEvent.LoadTaskDetails -> {
                currentTaskId = event.taskId
                loadTask(event.taskId)
            }
            is HousekeepingDetailsEvent.UpdateTaskStatus -> updateTaskStatus(event.newStatus)
            HousekeepingDetailsEvent.StartCleaningClicked -> updateTaskStatus(TaskStatus.CLEANING)
            HousekeepingDetailsEvent.CompleteTaskClicked -> updateTaskStatus(TaskStatus.COMPLETED)
            HousekeepingDetailsEvent.ReportMaintenanceClicked -> updateTaskStatus(TaskStatus.MAINTENANCE)
            HousekeepingDetailsEvent.RequestInspectionClicked -> updateTaskStatus(TaskStatus.INSPECTED)
            HousekeepingDetailsEvent.Retry -> currentTaskId?.let { loadTask(it) }
        }
    }

    private fun loadTask(taskId: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                getTaskByIdUseCase(taskId).collect { task ->
                    if (task != null) {
                        _uiState.update { it.copy(isLoading = false, task = task) }
                    } else {
                        _uiState.update { it.copy(isLoading = false, error = "Task not found") }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load task") }
            }
        }
    }

    private fun updateTaskStatus(newStatus: TaskStatus) {
        val taskId = currentTaskId ?: return
        viewModelScope.launch {
            try {
                updateTaskStatusUseCase(taskId, newStatus)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Failed to update status") }
            }
        }
    }
}
