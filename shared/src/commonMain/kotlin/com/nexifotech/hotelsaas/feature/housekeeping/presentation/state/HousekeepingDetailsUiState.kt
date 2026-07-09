package com.nexifotech.hotelsaas.feature.housekeeping.presentation.state

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask

data class HousekeepingDetailsUiState(
    val isLoading: Boolean = true,
    val task: HousekeepingTask? = null,
    val error: String? = null
)
