package com.nexifotech.hotelsaas.feature.housekeeping.data.repository

import com.nexifotech.hotelsaas.feature.housekeeping.data.datasource.HousekeepingDataSource
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus
import com.nexifotech.hotelsaas.feature.housekeeping.domain.repository.HousekeepingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HousekeepingRepositoryImpl(
    private val dataSource: HousekeepingDataSource
) : HousekeepingRepository {

    override fun getTasks(): Flow<List<HousekeepingTask>> {
        return dataSource.getTasks()
    }

    override fun getTaskById(taskId: String): Flow<HousekeepingTask?> {
        return dataSource.getTasks().map { tasks ->
            tasks.find { it.id == taskId }
        }
    }

    override fun searchTasks(query: String): Flow<List<HousekeepingTask>> {
        return dataSource.getTasks().map { tasks ->
            if (query.isBlank()) {
                tasks
            } else {
                tasks.filter {
                    it.roomNumber.contains(query, ignoreCase = true) ||
                    it.assignedStaff.contains(query, ignoreCase = true) ||
                    it.id.contains(query, ignoreCase = true)
                }
            }
        }
    }

    override fun filterTasks(status: TaskStatus?): Flow<List<HousekeepingTask>> {
        return dataSource.getTasks().map { tasks ->
            if (status == null) {
                tasks
            } else {
                tasks.filter { it.status == status }
            }
        }
    }

    override fun getSummary(): Flow<HousekeepingSummary> {
        return dataSource.getSummary()
    }

    override suspend fun updateTaskStatus(taskId: String, newStatus: TaskStatus) {
        dataSource.updateTaskStatus(taskId, newStatus.name)
    }
}
