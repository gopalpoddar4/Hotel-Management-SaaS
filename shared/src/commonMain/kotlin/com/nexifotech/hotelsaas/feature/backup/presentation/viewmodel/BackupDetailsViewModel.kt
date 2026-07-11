package com.nexifotech.hotelsaas.feature.backup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.backup.data.repository.DummyBackupRepository
import com.nexifotech.hotelsaas.feature.backup.domain.repository.BackupRepository
import com.nexifotech.hotelsaas.feature.backup.presentation.state.BackupDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch

class BackupDetailsViewModel(
    private val backupId: String,
    private val repository: BackupRepository = DummyBackupRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(BackupDetailsUiState(isLoading = true))
    val uiState: StateFlow<BackupDetailsUiState> = _uiState.asStateFlow()

    init {
        loadBackupDetails()
    }

    private fun loadBackupDetails() {
        viewModelScope.launch {
            repository.getBackupById(backupId)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { backup ->
                    if (backup != null) {
                        _uiState.update { it.copy(isLoading = false, backup = backup) }
                    } else {
                        _uiState.update { it.copy(isLoading = false, error = "Backup not found") }
                    }
                }
        }
    }
}
