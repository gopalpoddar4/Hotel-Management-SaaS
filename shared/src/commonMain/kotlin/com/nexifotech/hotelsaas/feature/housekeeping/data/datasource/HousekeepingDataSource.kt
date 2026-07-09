package com.nexifotech.hotelsaas.feature.housekeeping.data.datasource

import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingSummary
import com.nexifotech.hotelsaas.feature.housekeeping.domain.model.HousekeepingTask
import kotlinx.coroutines.flow.Flow

interface HousekeepingDataSource {
    fun getTasks(): Flow<List<HousekeepingTask>>
    fun getSummary(): Flow<HousekeepingSummary>
    suspend fun updateTaskStatus(taskId: String, newStatus: String)
}
