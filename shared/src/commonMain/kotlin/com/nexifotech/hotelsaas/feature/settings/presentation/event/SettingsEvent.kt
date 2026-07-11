package com.nexifotech.hotelsaas.feature.settings.presentation.event

import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings

sealed interface SettingsEvent {
    data class SearchQueryChanged(val query: String) : SettingsEvent
    data class UpdateSettings(val newSettings: AppSettings) : SettingsEvent
    data object SaveSettings : SettingsEvent
    data object ResetSettings : SettingsEvent
    data object ClearSaveSuccess : SettingsEvent
}
