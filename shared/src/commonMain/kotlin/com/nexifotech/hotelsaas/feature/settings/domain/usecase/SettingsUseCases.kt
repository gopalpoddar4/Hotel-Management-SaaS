package com.nexifotech.hotelsaas.feature.settings.domain.usecase

import com.nexifotech.hotelsaas.feature.settings.domain.model.AppSettings
import com.nexifotech.hotelsaas.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<AppSettings> {
        return repository.getSettings()
    }
}

class SaveSettingsUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(settings: AppSettings) {
        repository.saveSettings(settings)
    }
}

class ResetSettingsUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke() {
        repository.resetSettings()
    }
}

data class SettingsUseCases(
    val getSettings: GetSettingsUseCase,
    val saveSettings: SaveSettingsUseCase,
    val resetSettings: ResetSettingsUseCase
)
