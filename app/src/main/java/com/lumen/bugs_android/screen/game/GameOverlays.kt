package com.lumen.bugs_android.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lumen.bugs_android.R

@Composable
fun BoxScope.GameOverlays(
    state: GameState,
    playerName: String?,
    onAction: (GameAction) -> Unit,
) {
    if (state.isIdle) {
        GameOverlay(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = stringResource(R.string.game_title),
                style = MaterialTheme.typography.titleLarge,
            )
            Button(onClick = { onAction(GameAction.OnStart) }) {
                Text(stringResource(R.string.game_start))
            }
        }
    }

    if (state.isPaused) {
        PauseOverlay(
            onResume = { onAction(GameAction.OnResume) },
            onRestart = { onAction(GameAction.OnRestart) },
            onExit = { onAction(GameAction.OnExit) },
            modifier = Modifier.align(Alignment.Center),
        )
    }

    if (state.isFinished) {
        RoundOverOverlay(
            state = state,
            playerName = playerName,
            onRestart = { onAction(GameAction.OnRestart) },
            onExit = { onAction(GameAction.OnExit) },
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun PauseOverlay(
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameOverlay(modifier = modifier) {
        Text(
            text = stringResource(R.string.game_pause),
            style = MaterialTheme.typography.titleLarge,
        )
        Button(onClick = onResume) {
            Text(stringResource(R.string.game_resume))
        }
        Button(onClick = onRestart) {
            Text(stringResource(R.string.game_restart))
        }
        Button(onClick = onExit) {
            Text(stringResource(R.string.game_back_to_menu))
        }
    }
}

@Composable
private fun RoundOverOverlay(
    state: GameState,
    playerName: String?,
    onRestart: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameOverlay(modifier = modifier) {
        Text(
            text = stringResource(R.string.game_round_over),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.game_score, state.score),
            style = MaterialTheme.typography.titleMedium,
        )
        if (state.resultSaved && playerName != null) {
            Text(
                text = stringResource(R.string.game_result_saved, playerName),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        BugType.entries.forEach { type ->
            Text(
                text = stringResource(
                    R.string.game_caught_by_type,
                    stringResource(type.labelRes),
                    state.caught[type] ?: 0,
                ),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            text = stringResource(R.string.game_misses, state.misses),
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = stringResource(R.string.game_accuracy, state.accuracyPercent),
            style = MaterialTheme.typography.bodyMedium,
        )
        Button(onClick = onRestart) {
            Text(stringResource(R.string.game_restart))
        }
        Button(onClick = onExit) {
            Text(stringResource(R.string.game_back_to_menu))
        }
    }
}

@Composable
private fun GameOverlay(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 6.dp,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content,
        )
    }
}
