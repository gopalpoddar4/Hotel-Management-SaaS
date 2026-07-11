package com.nexifotech.hotelsaas.feature.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings
import com.nexifotech.hotelsaas.feature.settings.domain.usecase.SettingsUseCases
import com.nexifotech.hotelsaas.feature.settings.presentation.event.SettingsEvent
import com.nexifotech.hotelsaas.feature.settings.presentation.state.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val useCases: SettingsUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            useCases.getSettings()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load settings") }
                }
                .collect { settings ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            settings = settings,
                            error = null
                        )
                    }
                    filterCategories(_uiState.value.searchQuery, settings)
                }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                filterCategories(event.query, _uiState.value.settings)
            }
            is SettingsEvent.UpdateSettings -> {
                _uiState.update { it.copy(settings = event.newSettings) }
            }
            is SettingsEvent.SaveSettings -> {
                saveSettings()
            }
            is SettingsEvent.ResetSettings -> {
                resetSettings()
            }
            is SettingsEvent.ClearSaveSuccess -> {
                _uiState.update { it.copy(saveSuccess = false) }
            }
        }
    }

    private fun saveSettings() {
        val currentSettings = _uiState.value.settings ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                useCases.saveSettings(currentSettings)
                _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = e.message ?: "Failed to save settings") }
            }
        }
    }

    private fun resetSettings() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                useCases.resetSettings()
                _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = e.message ?: "Failed to reset settings") }
            }
        }
    }

    private fun filterCategories(query: String, settings: AppSettings?) {
        if (settings == null) return
        if (query.isBlank()) {
            _uiState.update { it.copy(filteredCategories = getAllCategories()) }
            return
        }

        val q = query.lowercase()
        val filtered = mutableListOf<String>()

        // Very basic mock filtering by category name
        if ("hotel".contains(q) || "profile".contains(q)) filtered.add("Hotel Profile")
        if ("general".contains(q)) filtered.add("General")
        if ("appearance".contains(q) || "theme".contains(q)) filtered.add("Appearance")
        if ("billing".contains(q) || "tax".contains(q)) filtered.add("Billing")
        if ("reservation".contains(q) || "booking".contains(q)) filtered.add("Reservation")
        if ("notification".contains(q) || "reminder".contains(q)) filtered.add("Notification")
        if ("email".contains(q) || "sms".contains(q)) filtered.add("Email & SMS")
        if ("printer".contains(q)) filtered.add("Printer")
        if ("security".contains(q) || "password".contains(q)) filtered.add("Security")
        if ("backup".contains(q)) filtered.add("Backup")
        if ("system".contains(q) || "information".contains(q)) filtered.add("System Information")
        if ("about".contains(q) || "application".contains(q)) filtered.add("About Application")

        _uiState.update { it.copy(filteredCategories = filtered) }
    }

    private fun getAllCategories(): List<String> = listOf(
        "Hotel Profile",
        "General",
        "Appearance",
        "Billing",
        "Reservation",
        "Notification",
        "Email & SMS",
        "Printer",
        "Security",
        "Backup",
        "System Information",
        "About Application"
    )
}
