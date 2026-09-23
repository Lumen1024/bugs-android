package com.lumen.bugs_android.game

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.bugs_android.data.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private const val COUNTDOWN_SECONDS = 3
private const val FRAME_DELAY_MS = 16L
private const val BUG_MIN_SPEED = 0.08f
private const val BUG_MAX_SPEED = 0.20f

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
    val bugs: List<Bug> = emptyList(),
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

    private var roundJob: Job? = null
    private var movementJob: Job? = null

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
        cancelJobs()
        _state.value = GameState(
            status = GameStatus.Countdown,
            countdownSeconds = COUNTDOWN_SECONDS,
            timeLeftSeconds = roundDuration(),
            bugs = spawnBugs(),
        )
        roundJob = viewModelScope.launch {
            while (_state.value.countdownSeconds > 0) {
                delay(1_000)
                _state.update { it.copy(countdownSeconds = (it.countdownSeconds - 1).coerceAtLeast(0)) }
            }
            beginRound()
        }
    }

    private fun beginRound() {
        _state.update { it.copy(status = GameStatus.Playing) }
        startRoundTimer()
        startMovement()
    }

    private fun startRoundTimer() {
        roundJob = viewModelScope.launch {
            while (_state.value.timeLeftSeconds > 0) {
                delay(1_000)
                _state.update { it.copy(timeLeftSeconds = (it.timeLeftSeconds - 1).coerceAtLeast(0)) }
            }
            finishRound()
        }
    }

    private fun startMovement() {
        movementJob?.cancel()
        movementJob = viewModelScope.launch {
            val dt = FRAME_DELAY_MS / 1000f
            while (true) {
                delay(FRAME_DELAY_MS)
                val gameSpeed = settingsRepository.settings.value.gameSpeed
                _state.update { state ->
                    state.copy(
                        bugs = state.bugs.map { it.advance(dt, gameSpeed * it.type.speedFactor) },
                    )
                }
            }
        }
    }

    private fun pause() {
        if (_state.value.status != GameStatus.Playing) return
        roundJob?.cancel()
        movementJob?.cancel()
        _state.update { it.copy(status = GameStatus.Paused) }
    }

    private fun resume() {
        if (_state.value.status != GameStatus.Paused) return
        _state.update { it.copy(status = GameStatus.Playing) }
        startRoundTimer()
        startMovement()
    }

    private fun finishRound() {
        movementJob?.cancel()
        _state.update { it.copy(status = GameStatus.Finished) }
    }

    private fun spawnBugs(): List<Bug> {
        val count = settingsRepository.settings.value.maxBugsCount
        return List(count) { index -> createBug(index.toLong()) }
    }

    private fun createBug(id: Long): Bug {
        val angle = Random.nextFloat() * 2f * PI.toFloat()
        val speed = BUG_MIN_SPEED + Random.nextFloat() * (BUG_MAX_SPEED - BUG_MIN_SPEED)
        return Bug(
            id = id,
            type = BugType.entries.random(),
            position = Offset(Random.nextFloat(), Random.nextFloat()),
            velocity = Offset(cos(angle) * speed, sin(angle) * speed),
        )
    }

    private fun roundDuration(): Int = settingsRepository.settings.value.roundDurationSeconds

    private fun cancelJobs() {
        roundJob?.cancel()
        movementJob?.cancel()
    }

    override fun onCleared() {
        cancelJobs()
    }
}
