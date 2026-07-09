package com.nexifotech.hotelsaas.feature.housekeeping.presentation.event

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus

sealed interface HousekeepingEvent {
    data object LoadTasks : HousekeepingEvent
    data class OnFilterSelected(val filter: TaskStatus?) : HousekeepingEvent
    data class OnSearchQueryChanged(val query: String) : HousekeepingEvent
    data class OnTaskClicked(val taskId: String) : HousekeepingEvent
}
