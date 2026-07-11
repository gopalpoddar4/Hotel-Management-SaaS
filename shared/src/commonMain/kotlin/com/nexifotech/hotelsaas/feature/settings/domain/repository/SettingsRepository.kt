package com.nexifotech.hotelsaas.feature.settings.domain.repository

import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun saveSettings(settings: AppSettings)
    suspend fun resetSettings()
}
