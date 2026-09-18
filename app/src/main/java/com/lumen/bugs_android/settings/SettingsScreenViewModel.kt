package com.lumen.bugs_android.settings

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.data.SettingsRepository
import com.lumen.bugs_android.model.GameSettings
import kotlinx.coroutines.flow.StateFlow

sealed class SettingsScreenAction {
    data class OnGameSpeedChange(val gameSpeed: Float) : SettingsScreenAction()
    data class OnMaxBugsCountChange(val maxBugsCount: Int) : SettingsScreenAction()
    data class OnBonusIntervalChange(val intervalSeconds: Int) : SettingsScreenAction()
    data class OnRoundDurationChange(val seconds: Int) : SettingsScreenAction()
}

class SettingsScreenViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    val state: StateFlow<GameSettings> = settingsRepository.settings

    fun onAction(action: SettingsScreenAction) {
        settingsRepository.update { settings ->
            when (action) {
                is SettingsScreenAction.OnGameSpeedChange -> settings.copy(gameSpeed = action.gameSpeed)
                is SettingsScreenAction.OnMaxBugsCountChange -> settings.copy(maxBugsCount = action.maxBugsCount)
                is SettingsScreenAction.OnBonusIntervalChange -> settings.copy(bonusIntervalSeconds = action.intervalSeconds)
                is SettingsScreenAction.OnRoundDurationChange -> settings.copy(roundDurationSeconds = action.seconds)
            }
        }
    }
}
