package com.lumen.bugs_android.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.bugs_android.data.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val COUNTDOWN_SECONDS = 3

enum class GameStatus {
    Idle,
    Countdown,
    Playing,
    Paused,
    Finished,
}

data class GameState(
    val status: GameStatus = GameStatus.Idle,
    val score: Int = 0,
    val timeLeftSeconds: Int = 0,
    val countdownSeconds: Int = 0,
)

sealed class GameAction {
    data object OnStart : GameAction()
    data object OnPause : GameAction()
    data object OnResume : GameAction()
    data object OnRestart : GameAction()
}

class GameViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    init {
        start()
    }

    fun onAction(action: GameAction) {
        when (action) {
            GameAction.OnStart -> start()
            GameAction.OnPause -> pause()
            GameAction.OnResume -> resume()
            GameAction.OnRestart -> start()
        }
    }

    private fun start() {
        timerJob?.cancel()
        _state.value = GameState(
            status = GameStatus.Countdown,
            countdownSeconds = COUNTDOWN_SECONDS,
            timeLeftSeconds = roundDuration(),
        )
        timerJob = viewModelScope.launch {
            while (_state.value.countdownSeconds > 0) {
                delay(1_000)
                _state.update { it.copy(countdownSeconds = (it.countdownSeconds - 1).coerceAtLeast(0)) }
            }
            beginRound()
        }
    }

    private fun beginRound() {
        _state.update { it.copy(status = GameStatus.Playing) }
        runRoundTimer()
    }

    private fun pause() {
        if (_state.value.status != GameStatus.Playing) return
        timerJob?.cancel()
        _state.update { it.copy(status = GameStatus.Paused) }
    }

    private fun resume() {
        if (_state.value.status != GameStatus.Paused) return
        _state.update { it.copy(status = GameStatus.Playing) }
        runRoundTimer()
    }

    private fun runRoundTimer() {
        timerJob = viewModelScope.launch {
            while (_state.value.timeLeftSeconds > 0) {
                delay(1_000)
                _state.update { it.copy(timeLeftSeconds = (it.timeLeftSeconds - 1).coerceAtLeast(0)) }
            }
            _state.update { it.copy(status = GameStatus.Finished) }
        }
    }

    private fun roundDuration(): Int = settingsRepository.settings.value.roundDurationSeconds

    override fun onCleared() {
        timerJob?.cancel()
    }
}
