package com.nexifotech.hotelsaas.feature.backup.presentation.state

import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupInfo
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupSchedule
import com.nexifotech.hotelsaas.feature.backup.domain.model.StorageUsage

data class BackupUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val backups: List<BackupInfo> = emptyList(),
    val storageUsage: StorageUsage? = null,
    val schedule: BackupSchedule? = null,
    val searchQuery: String = "",
    val totalBackups: Int = 0,
    val successfulBackups: Int = 0,
    val failedBackups: Int = 0,
    val lastBackupTime: String = ""
)
