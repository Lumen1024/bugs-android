package com.lumen.bugs_android.repository

import com.lumen.bugs_android.model.GameSettings
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val settings: StateFlow<GameSettings>

    fun update(transform: (GameSettings) -> GameSettings)
}
