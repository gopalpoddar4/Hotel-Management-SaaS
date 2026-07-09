package com.nexifotech.hotelsaas.feature.housekeeping.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.housekeeping.data.datasource.FakeHousekeepingDataSource
import com.nexifotech.hotelsaas.feature.housekeeping.data.repository.HousekeepingRepositoryImpl
import com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase.FilterTasksUseCase
import com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase.GetHousekeepingSummaryUseCase
import com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase.GetHousekeepingTasksUseCase
import com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase.SearchTasksUseCase
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.event.HousekeepingEvent
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.state.HousekeepingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HousekeepingViewModel : ViewModel() {

    private val dataSource = FakeHousekeepingDataSource()
    private val repository = HousekeepingRepositoryImpl(dataSource)

    private val getTasksUseCase = GetHousekeepingTasksUseCase(repository)
    private val searchTasksUseCase = SearchTasksUseCase(repository)
    private val filterTasksUseCase = FilterTasksUseCase(repository)
    private val getSummaryUseCase = GetHousekeepingSummaryUseCase(repository)

    private val _uiState = MutableStateFlow(HousekeepingUiState())
    val uiState: StateFlow<HousekeepingUiState> = _uiState.asStateFlow()

    init {
        loadData()

        viewModelScope.launch {
            getTasksUseCase().collect { tasks ->
                _uiState.update { it.copy(allTasks = tasks) }
                applyFilters()
                updateSummary()
            }
        }
    }

    fun onEvent(event: HousekeepingEvent) {
        when (event) {
            is HousekeepingEvent.LoadTasks -> loadData()
            is HousekeepingEvent.OnFilterSelected -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                applyFilters()
            }
            is HousekeepingEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                applyFilters()
            }
            is HousekeepingEvent.OnTaskClicked -> {
                // Handled via UI navigation callback
            }
        }
    }

    private fun loadData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                updateSummary()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "An error occurred") }
            }
        }
    }

    private fun applyFilters() {
        viewModelScope.launch {
            val query = _uiState.value.searchQuery
            val filter = _uiState.value.selectedFilter

            searchTasksUseCase(query).collect { searchResults ->
                val finalResults = if (filter != null) {
                    searchResults.filter { it.status == filter }
                } else {
                    searchResults
                }
                _uiState.update { it.copy(filteredTasks = finalResults) }
            }
        }
    }

    private fun updateSummary() {
        viewModelScope.launch {
            getSummaryUseCase().collect { summary ->
                _uiState.update { it.copy(summary = summary) }
            }
        }
    }
}
