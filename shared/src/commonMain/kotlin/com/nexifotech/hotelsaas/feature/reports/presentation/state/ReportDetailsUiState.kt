package com.nexifotech.hotelsaas.feature.reports.presentation.state

import com.nexifotech.hotelsaas.feature.reports.domain.model.ReportDetailsData

data class ReportDetailsUiState(
    val isLoading: Boolean = false,
    val reportDetails: ReportDetailsData? = null,
    val error: String? = null
)
