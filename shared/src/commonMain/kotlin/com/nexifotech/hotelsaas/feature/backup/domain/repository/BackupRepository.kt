package com.nexifotech.hotelsaas.feature.backup.domain.repository

import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupInfo
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupSchedule
import com.nexifotech.hotelsaas.feature.backup.domain.model.StorageUsage
import kotlinx.coroutines.flow.Flow

interface BackupRepository {
    fun getBackups(): Flow<List<BackupInfo>>
    fun getBackupById(id: String): Flow<BackupInfo?>
    fun getStorageUsage(): Flow<StorageUsage>
    fun getBackupSchedule(): Flow<BackupSchedule>
    
    // Actions
    suspend fun startBackup(type: String, location: String): Result<Unit>
    suspend fun restoreBackup(id: String): Result<Unit>
    suspend fun deleteBackup(id: String): Result<Unit>
}
