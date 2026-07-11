package com.nexifotech.hotelsaas.feature.backup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.backup.data.repository.DummyBackupRepository
import com.nexifotech.hotelsaas.feature.backup.domain.model.BackupStatus
import com.nexifotech.hotelsaas.feature.backup.domain.repository.BackupRepository
import com.nexifotech.hotelsaas.feature.backup.presentation.state.BackupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.catch

class BackupViewModel(
    private val repository: BackupRepository = DummyBackupRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(BackupUiState(isLoading = true))
    val uiState: StateFlow<BackupUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                combine(
                    repository.getBackups(),
                    repository.getStorageUsage(),
                    repository.getBackupSchedule()
                ) { backups, storage, schedule ->
                    val successCount = backups.count { it.status == BackupStatus.SUCCESS }
                    val failedCount = backups.count { it.status == BackupStatus.FAILED }
                    val lastBackup = backups.filter { it.status == BackupStatus.SUCCESS }
                        .maxByOrNull { it.createdAt }?.createdAt ?: "N/A"

                    BackupUiState(
                        isLoading = false,
                        backups = backups,
                        storageUsage = storage,
                        schedule = schedule,
                        totalBackups = backups.size,
                        successfulBackups = successCount,
                        failedBackups = failedCount,
                        lastBackupTime = lastBackup
                    )
                }.catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        // For a real app, you'd trigger a flow combine here to filter backups based on search query
    }
}
