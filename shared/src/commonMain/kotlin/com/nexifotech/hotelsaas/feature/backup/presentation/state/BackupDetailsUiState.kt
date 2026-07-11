package com.nexifotech.hotelsaas.feature.backup.presentation.state

import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupInfo

data class BackupDetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val backup: BackupInfo? = null
)
