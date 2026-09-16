package com.lumen.bugs_android.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsScreenState(
    val gameSpeed: Float = 1f,
    val maxBugsCount: Int = 10,
    val bonusAppearanceInterval: Int = 10,
    val roundDurationSeconds: Int = 60,
)

sealed class SettingsScreenAction {
    data class OnGameSpeedChange(val gameSpeed: Float) : SettingsScreenAction()
    data class OnMaxBugsCountChange(val maxBugsCount: Int) : SettingsScreenAction()
    data class OnBonusIntervalChange(val interval: Int) : SettingsScreenAction()
    data class OnRoundDurationChange(val seconds: Int) : SettingsScreenAction()
}

class SettingsScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: SettingsScreenAction) {
        when (action) {
            is SettingsScreenAction.OnGameSpeedChange -> _state.update { it.copy(gameSpeed = action.gameSpeed) }
            is SettingsScreenAction.OnMaxBugsCountChange -> _state.update { it.copy(maxBugsCount = action.maxBugsCount) }
            is SettingsScreenAction.OnBonusIntervalChange -> _state.update { it.copy(bonusAppearanceInterval = action.interval) }
            is SettingsScreenAction.OnRoundDurationChange -> _state.update { it.copy(roundDurationSeconds = action.seconds) }
        }
    }
}
