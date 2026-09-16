package com.lumen.bugs_android.settings

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.model.GameSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsScreenState(
    val settings: GameSettings = GameSettings(),
)

sealed class SettingsScreenAction {
    data class OnGameSpeedChange(val gameSpeed: Float) : SettingsScreenAction()
    data class OnMaxBugsCountChange(val maxBugsCount: Int) : SettingsScreenAction()
    data class OnBonusIntervalChange(val intervalSeconds: Int) : SettingsScreenAction()
    data class OnRoundDurationChange(val seconds: Int) : SettingsScreenAction()
}

class SettingsScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: SettingsScreenAction) {
        when (action) {
            is SettingsScreenAction.OnGameSpeedChange ->
                update { it.copy(gameSpeed = action.gameSpeed) }
            is SettingsScreenAction.OnMaxBugsCountChange ->
                update { it.copy(maxBugsCount = action.maxBugsCount) }
            is SettingsScreenAction.OnBonusIntervalChange ->
                update { it.copy(bonusIntervalSeconds = action.intervalSeconds) }
            is SettingsScreenAction.OnRoundDurationChange ->
                update { it.copy(roundDurationSeconds = action.seconds) }
        }
    }

    private fun update(transform: (GameSettings) -> GameSettings) {
        _state.update { it.copy(settings = transform(it.settings)) }
    }
}
