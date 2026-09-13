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
    data class OnMaxBugsCountChanged(val maxBugsCount: Int) : SettingsScreenAction()
    data object OnSaveButtonClick : SettingsScreenAction()
}

class SettingsScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()


    fun onAction(action: SettingsScreenAction) {
        when (action) {
            is SettingsScreenAction.OnGameSpeedChange -> _state.update { it.copy(gameSpeed = action.gameSpeed) }
            is SettingsScreenAction.OnMaxBugsCountChanged -> TODO()
            SettingsScreenAction.OnSaveButtonClick -> TODO()
        }
    }
}