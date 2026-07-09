package com.nexifotech.hotelsaas.feature.housekeeping.presentation.event

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus

sealed interface HousekeepingDetailsEvent {
    data class LoadTaskDetails(val taskId: String) : HousekeepingDetailsEvent
    data class UpdateTaskStatus(val newStatus: TaskStatus) : HousekeepingDetailsEvent
    data object StartCleaningClicked : HousekeepingDetailsEvent
    data object CompleteTaskClicked : HousekeepingDetailsEvent
    data object ReportMaintenanceClicked : HousekeepingDetailsEvent
    data object RequestInspectionClicked : HousekeepingDetailsEvent
    data object Retry : HousekeepingDetailsEvent
}
