package com.nexifotech.hotelsaas.feature.settings.presentation.state

import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings

data class SettingsUiState(
    val settings: AppSettings? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = "",
    val filteredCategories: List<String> = emptyList(), // Store category IDs that match the search
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)
