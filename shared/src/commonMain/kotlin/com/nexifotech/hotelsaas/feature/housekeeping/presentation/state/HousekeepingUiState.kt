package com.nexifotech.hotelsaas.feature.housekeeping.presentation.state

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus

data class HousekeepingUiState(
    val isLoading: Boolean = true,
    val allTasks: List<HousekeepingTask> = emptyList(),
    val filteredTasks: List<HousekeepingTask> = emptyList(),
    val searchQuery: String = "",
    val filters: List<TaskStatus?> = listOf(
        null, // All
        TaskStatus.DIRTY,
        TaskStatus.CLEANING,
        TaskStatus.READY,
        TaskStatus.INSPECTED,
        TaskStatus.MAINTENANCE,
        TaskStatus.COMPLETED
    ),
    val selectedFilter: TaskStatus? = null,
    val summary: HousekeepingSummary? = null,
    val error: String? = null
)
