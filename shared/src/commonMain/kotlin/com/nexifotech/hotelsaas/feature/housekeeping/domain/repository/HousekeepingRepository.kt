package com.nexifotech.hotelsaas.feature.housekeeping.domain.repository

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow

interface HousekeepingRepository {
    fun getTasks(): Flow<List<HousekeepingTask>>
    fun getTaskById(taskId: String): Flow<HousekeepingTask?>
    fun searchTasks(query: String): Flow<List<HousekeepingTask>>
    fun filterTasks(status: TaskStatus?): Flow<List<HousekeepingTask>>
    fun getSummary(): Flow<HousekeepingSummary>
    
    suspend fun updateTaskStatus(taskId: String, newStatus: TaskStatus)
}
