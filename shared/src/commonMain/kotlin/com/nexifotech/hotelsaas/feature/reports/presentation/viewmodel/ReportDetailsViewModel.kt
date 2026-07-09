package com.nexifotech.hotelsaas.feature.reports.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.reports.data.datasource.FakeReportsDataSource
import com.nexifotech.hotelsaas.feature.reports.data.repository.ReportsRepositoryImpl
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.GetReportsDashboardUseCase
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.GetReportsUseCase
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.GetReportDetailsUseCase
import com.nexifotech.hotelsaas.feature.reports.domain.usecase.ReportsUseCases
import com.nexifotech.hotelsaas.feature.reports.presentation.event.ReportDetailsEvent
import com.nexifotech.hotelsaas.feature.reports.presentation.state.ReportDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportDetailsViewModel : ViewModel() {

    private val useCases = ReportsUseCases(
        getDashboardMetrics = GetReportsDashboardUseCase(ReportsRepositoryImpl(FakeReportsDataSource())),
        getReports = GetReportsUseCase(ReportsRepositoryImpl(FakeReportsDataSource())),
        getReportDetails = GetReportDetailsUseCase(ReportsRepositoryImpl(FakeReportsDataSource()))
    )

    private val _uiState = MutableStateFlow(ReportDetailsUiState())
    val uiState: StateFlow<ReportDetailsUiState> = _uiState.asStateFlow()
    
    private var currentReportId: String? = null

    fun onEvent(event: ReportDetailsEvent) {
        when (event) {
            is ReportDetailsEvent.LoadReport -> {
                currentReportId = event.reportId
                loadReportDetails(event.reportId)
            }
            is ReportDetailsEvent.Refresh -> {
                currentReportId?.let { loadReportDetails(it) }
            }
            is ReportDetailsEvent.ExportPdf -> {
                // Dummy export action
            }
            is ReportDetailsEvent.ExportExcel -> {
                // Dummy export action
            }
            is ReportDetailsEvent.Share -> {
                // Dummy share action
            }
        }
    }

    private fun loadReportDetails(reportId: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = useCases.getReportDetails(reportId)
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    reportDetails = result.getOrNull(),
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }
}
