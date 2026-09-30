package com.lumen.bugs_android.screen.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreenRoot(
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GameScreen(state, viewModel::onAction, onExit, modifier)
}

@Composable
fun GameScreen(
    state: GameState,
    onAction: (GameAction) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler(enabled = state.isRoundActive) { onAction(GameAction.OnPause) }

    Box(modifier = modifier.fillMaxSize()) {
        GameField(
            bugs = state.bugs,
            enabled = state.isPlaying,
            onBugHit = { onAction(GameAction.OnBugHit(it)) },
            onMiss = { onAction(GameAction.OnMiss) },
            modifier = Modifier.fillMaxSize(),
        )

        GameHud(
            score = state.score,
            timeLeftSeconds = state.timeLeftSeconds,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        GameOverlays(
            state = state,
            onAction = onAction,
            onExit = onExit,
        )
    }
}
