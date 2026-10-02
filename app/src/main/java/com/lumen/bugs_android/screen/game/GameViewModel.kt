package com.lumen.bugs_android.screen.game

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.bugs_android.model.Profile
import com.lumen.bugs_android.repository.CurrentProfileRepository
import com.lumen.bugs_android.repository.GameResultRepository
import com.lumen.bugs_android.repository.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

private const val FRAME_DELAY_MS = 16L
private const val BUG_MIN_SPEED = 0.08f
private const val BUG_MAX_SPEED = 0.20f
private const val MISS_PENALTY = 1

enum class GameStatus {
    Idle,
    Playing,
    Paused,
    Finished,
}

data class GameState(
    val status: GameStatus = GameStatus.Idle,
    val score: Int = 0,
    val timeLeftSeconds: Int = 0,
    val bugs: List<Bug> = emptyList(),
    val caught: Map<BugType, Int> = emptyMap(),
    val misses: Int = 0,
    val resultSaved: Boolean = false,
) {
    val isIdle: Boolean get() = status == GameStatus.Idle
    val isPlaying: Boolean get() = status == GameStatus.Playing
    val isPaused: Boolean get() = status == GameStatus.Paused
    val isFinished: Boolean get() = status == GameStatus.Finished

    val hits: Int get() = caught.values.sum()
    val accuracyPercent: Int
        get() = if (hits + misses == 0) 0 else (hits * 100f / (hits + misses)).roundToInt()
}

sealed class GameAction {
    data object OnStart : GameAction()
    data object OnPause : GameAction()
    data object OnResume : GameAction()
    data object OnRestart : GameAction()
    data object OnExit : GameAction()
    data object OnSwitchProfile : GameAction()
    data class OnBugHit(val bugId: Long) : GameAction()
    data object OnMiss : GameAction()
}

class GameViewModel(
    private val settingsRepository: SettingsRepository,
    private val currentProfileRepository: CurrentProfileRepository,
    private val gameResultRepository: GameResultRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    val currentProfile: StateFlow<Profile?> = currentProfileRepository.currentProfile

    private var roundJob: Job? = null
    private var movementJob: Job? = null
    private var nextBugId = 0L

    fun onAction(action: GameAction) {
        when (action) {
            GameAction.OnStart -> start()
            GameAction.OnPause -> pause()
            GameAction.OnResume -> resume()
            GameAction.OnRestart -> start()
            GameAction.OnExit -> exit()
            GameAction.OnSwitchProfile -> switchProfile()
            is GameAction.OnBugHit -> hitBug(action.bugId)
            GameAction.OnMiss -> miss()
        }
    }

    private fun start() {
        cancelJobs()
        _state.value = GameState(
            status = GameStatus.Playing,
            timeLeftSeconds = roundDuration(),
            bugs = spawnBugs(),
        )
        startRoundTimer()
        startMovement()
    }

    private fun startRoundTimer() {
        roundJob = viewModelScope.launch {
            while (_state.value.timeLeftSeconds > 0) {
                delay(1_000.milliseconds)
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
                delay(FRAME_DELAY_MS.milliseconds)
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
        saveResult()
    }

    private fun saveResult() {
        val profile = currentProfile.value ?: return
        val score = _state.value.score
        viewModelScope.launch {
            gameResultRepository.saveResult(profile.id, score, profile.difficulty)
                .onSuccess { _state.update { it.copy(resultSaved = true) } }
        }
    }

    private fun exit() {
        cancelJobs()
        _state.value = GameState()
    }

    private fun switchProfile() {
        viewModelScope.launch { currentProfileRepository.clear() }
    }

    private fun hitBug(id: Long) {
        if (_state.value.status != GameStatus.Playing) return
        _state.update { state ->
            val hit = state.bugs.firstOrNull { it.id == id } ?: return@update state
            state.copy(
                score = state.score + hit.type.points,
                caught = state.caught + (hit.type to (state.caught[hit.type] ?: 0) + 1),
                bugs = state.bugs.filterNot { it.id == id } + createBug(),
            )
        }
    }

    private fun miss() {
        if (_state.value.status != GameStatus.Playing) return
        _state.update {
            it.copy(
                score = (it.score - MISS_PENALTY).coerceAtLeast(0),
                misses = it.misses + 1,
            )
        }
    }

    private fun spawnBugs(): List<Bug> {
        val count = settingsRepository.settings.value.maxBugsCount
        return List(count) { createBug() }
    }

    private fun createBug(): Bug {
        val angle = Random.nextFloat() * 2f * PI.toFloat()
        val speed = BUG_MIN_SPEED + Random.nextFloat() * (BUG_MAX_SPEED - BUG_MIN_SPEED)
        return Bug(
            id = nextBugId++,
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
