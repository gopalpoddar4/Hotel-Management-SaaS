package com.nexifotech.hotelsaas.feature.housekeeping.domain.usecase

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus
import com.nexifotech.hotelsaas.feature.housekeeping.domain.repository.HousekeepingRepository
import kotlinx.coroutines.flow.Flow

class GetHousekeepingTasksUseCase(private val repository: HousekeepingRepository) {
    operator fun invoke(): Flow<List<HousekeepingTask>> {
        return repository.getTasks()
    }
}

class GetTaskByIdUseCase(private val repository: HousekeepingRepository) {
    operator fun invoke(id: String): Flow<HousekeepingTask?> {
        return repository.getTaskById(id)
    }
}

class SearchTasksUseCase(private val repository: HousekeepingRepository) {
    operator fun invoke(query: String): Flow<List<HousekeepingTask>> {
        return repository.searchTasks(query)
    }
}

class FilterTasksUseCase(private val repository: HousekeepingRepository) {
    operator fun invoke(status: TaskStatus?): Flow<List<HousekeepingTask>> {
        return repository.filterTasks(status)
    }
}

class GetHousekeepingSummaryUseCase(private val repository: HousekeepingRepository) {
    operator fun invoke(): Flow<HousekeepingSummary> {
        return repository.getSummary()
    }
}

class UpdateTaskStatusUseCase(private val repository: HousekeepingRepository) {
    suspend operator fun invoke(id: String, newStatus: TaskStatus) {
        repository.updateTaskStatus(id, newStatus)
    }
}
