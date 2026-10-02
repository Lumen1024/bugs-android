package com.lumen.bugs_android.screen.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.model.Profile
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentProfile by viewModel.currentProfile.collectAsStateWithLifecycle()
    GameScreen(state, currentProfile, viewModel::onAction, modifier)
}

@Composable
fun GameScreen(
    state: GameState,
    currentProfile: Profile?,
    onAction: (GameAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler(enabled = state.isPlaying) { onAction(GameAction.OnPause) }

    Column(modifier = modifier.fillMaxSize()) {
        if (state.isIdle) {
            ProfileHeader(
                name = currentProfile?.name,
                onSwitchProfile = { onAction(GameAction.OnSwitchProfile) },
            )
        } else {
            GameHud(
                score = state.score,
                timeLeftSeconds = state.timeLeftSeconds,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            GameField(
                bugs = state.bugs,
                enabled = state.isPlaying,
                onBugHit = { onAction(GameAction.OnBugHit(it)) },
                onMiss = { onAction(GameAction.OnMiss) },
                modifier = Modifier.fillMaxSize(),
            )

            GameOverlays(
                state = state,
                playerName = currentProfile?.name,
                onAction = onAction,
            )
        }
    }
}
