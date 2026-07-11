package com.nexifotech.hotelsaas.feature.backup.domain.model

data class BackupInfo(
    val id: String,
    val name: String,
    val type: BackupType,
    val size: String,
    val location: StorageLocation,
    val status: BackupStatus,
    val createdBy: String,
    val createdAt: String,
    val completedAt: String? = null,
    val duration: String,
    val databaseVersion: String,
    val isEncrypted: Boolean = true,
    val isCompressed: Boolean = true,
    val restoreTimeEstimated: String = "Unknown",
    val compatibility: String = "Fully Compatible"
)

enum class BackupType {
    FULL, INCREMENTAL, DATABASE_ONLY, MEDIA_ONLY
}

enum class StorageLocation {
    LOCAL, CLOUD_AWS, CLOUD_GCP, SERVER
}

enum class BackupStatus {
    SUCCESS, IN_PROGRESS, FAILED, PENDING
}
