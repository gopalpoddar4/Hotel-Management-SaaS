package com.nexifotech.hotelsaas.feature.backup.data.repository

import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupInfo
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupSchedule
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupStatus
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupType
import com.nexifotech.hotelsaas.feature.backup.domain.model.StorageLocation
import com.nexifotech.hotelsaas.feature.backup.domain.model.StorageUsage
import com.nexifotech.hotelsaas.feature.backup.domain.repository.BackupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.delay

class DummyBackupRepository : BackupRepository {

    private val dummyBackups = listOf(
        BackupInfo(
            id = "BKP-20240515-01",
            name = "Weekly Full Backup",
            type = BackupType.FULL,
            size = "4.2 GB",
            location = StorageLocation.CLOUD_AWS,
            status = BackupStatus.SUCCESS,
            createdBy = "System (Auto)",
            createdAt = "2024-05-15 02:00 AM",
            completedAt = "2024-05-15 02:45 AM",
            duration = "45 mins",
            databaseVersion = "v14.2",
            isEncrypted = true,
            isCompressed = true,
            restoreTimeEstimated = "~1 hour",
            compatibility = "Fully Compatible"
        ),
        BackupInfo(
            id = "BKP-20240514-02",
            name = "Daily DB Backup",
            type = BackupType.DATABASE_ONLY,
            size = "850 MB",
            location = StorageLocation.SERVER,
            status = BackupStatus.SUCCESS,
            createdBy = "System (Auto)",
            createdAt = "2024-05-14 02:00 AM",
            completedAt = "2024-05-14 02:10 AM",
            duration = "10 mins",
            databaseVersion = "v14.2",
            isEncrypted = true,
            isCompressed = true,
            restoreTimeEstimated = "~15 mins",
            compatibility = "Fully Compatible"
        ),
        BackupInfo(
            id = "BKP-20240513-01",
            name = "Manual Backup Pre-Deployment",
            type = BackupType.FULL,
            size = "4.1 GB",
            location = StorageLocation.LOCAL,
            status = BackupStatus.SUCCESS,
            createdBy = "Admin User",
            createdAt = "2024-05-13 11:30 PM",
            completedAt = "2024-05-14 12:20 AM",
            duration = "50 mins",
            databaseVersion = "v14.1",
            isEncrypted = true,
            isCompressed = true,
            restoreTimeEstimated = "~1 hour",
            compatibility = "Requires DB Downgrade"
        ),
        BackupInfo(
            id = "BKP-20240512-04",
            name = "Media Delta Sync",
            type = BackupType.MEDIA_ONLY,
            size = "1.2 GB",
            location = StorageLocation.CLOUD_GCP,
            status = BackupStatus.FAILED,
            createdBy = "System (Auto)",
            createdAt = "2024-05-12 04:00 AM",
            completedAt = "2024-05-12 04:05 AM",
            duration = "5 mins",
            databaseVersion = "N/A",
            isEncrypted = false,
            isCompressed = true,
            restoreTimeEstimated = "N/A",
            compatibility = "N/A"
        )
    )

    private val dummyStorageUsage = StorageUsage(
        localStorageUsed = "4.1 GB",
        localStorageTotal = "500 GB",
        localPercentage = 0.008f,
        cloudStorageUsed = "142.5 GB",
        cloudStorageTotal = "1000 GB",
        cloudPercentage = 0.1425f,
        databaseSize = "850 MB",
        mediaSize = "140 GB"
    )

    private val dummySchedule = BackupSchedule(
        isAutoBackupEnabled = true,
        dailySchedule = "02:00 AM",
        weeklySchedule = "Sunday, 01:00 AM",
        monthlySchedule = "1st of Month",
        retentionPolicy = "30 Days (Full), 7 Days (Incremental)",
        frequency = "Daily",
        time = "02:00 AM",
        notifyOnSuccess = true,
        notifyOnFailure = true
    )

    override fun getBackups(): Flow<List<BackupInfo>> = flowOf(dummyBackups)

    override fun getBackupById(id: String): Flow<BackupInfo?> = flowOf(dummyBackups.find { it.id == id })

    override fun getStorageUsage(): Flow<StorageUsage> = flowOf(dummyStorageUsage)

    override fun getBackupSchedule(): Flow<BackupSchedule> = flowOf(dummySchedule)

    override suspend fun startBackup(type: String, location: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    override suspend fun restoreBackup(id: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    override suspend fun deleteBackup(id: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }
}
