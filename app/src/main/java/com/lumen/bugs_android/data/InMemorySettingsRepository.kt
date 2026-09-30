package com.lumen.bugs_android.data

import com.lumen.bugs_android.model.GameSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemorySettingsRepository : SettingsRepository {
    private val _settings = MutableStateFlow(GameSettings())
    override val settings: StateFlow<GameSettings> = _settings.asStateFlow()

    override fun update(transform: (GameSettings) -> GameSettings) {
        _settings.update(transform)
    }
}
