package com.nexifotech.hotelsaas.feature.reports.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.reports.data.datasource.FakeReportsDataSource
import com.nexifotech.hotelsaas.feature.reports.data.repository.ReportsRepositoryImpl
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.GetReportsDashboardUseCase
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.GetReportsUseCase
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.GetReportDetailsUseCase
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.ReportsUseCases
import com.nexifotech.hotelsaas.feature.reports.presentation.event.ReportsEvent
import com.nexifotech.hotelsaas.feature.reports.presentation.state.ReportsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {

    // Ideally, these use cases should be injected via DI (e.g., Koin). 
    // Creating them manually for now as per constraints.
    private val useCases = ReportsUseCases(
        getDashboardMetrics = GetReportsDashboardUseCase(ReportsRepositoryImpl(FakeReportsDataSource())),
        getReports = GetReportsUseCase(ReportsRepositoryImpl(FakeReportsDataSource())),
        getReportDetails = GetReportDetailsUseCase(ReportsRepositoryImpl(FakeReportsDataSource()))
    )

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: ReportsEvent) {
        when (event) {
            is ReportsEvent.LoadReports -> loadData()
            is ReportsEvent.Refresh -> loadData()
            is ReportsEvent.SearchChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                fetchReports()
            }
            is ReportsEvent.FilterChanged -> {
                _uiState.update { it.copy(selectedCategory = event.category) }
                fetchReports()
            }
            is ReportsEvent.DateFilterChanged -> {
                _uiState.update { it.copy(selectedDateFilter = event.dateFilter) }
                fetchReports()
            }
        }
    }

    private fun loadData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val metricsResult = useCases.getDashboardMetrics()
            val reportsResult = useCases.getReports(
                query = _uiState.value.searchQuery,
                category = _uiState.value.selectedCategory,
                dateFilter = _uiState.value.selectedDateFilter
            )

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    metrics = metricsResult.getOrNull() ?: state.metrics,
                    reports = reportsResult.getOrDefault(emptyList()),
                    error = if (metricsResult.isFailure || reportsResult.isFailure) "Failed to load data" else null
                )
            }
        }
    }

    private fun fetchReports() {
        viewModelScope.launch {
            val reportsResult = useCases.getReports(
                query = _uiState.value.searchQuery,
                category = _uiState.value.selectedCategory,
                dateFilter = _uiState.value.selectedDateFilter
            )

            if (reportsResult.isSuccess) {
                _uiState.update { it.copy(reports = reportsResult.getOrDefault(emptyList())) }
            }
        }
    }
}
