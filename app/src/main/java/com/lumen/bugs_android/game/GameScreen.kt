package com.lumen.bugs_android.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.R
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
    Box(modifier = modifier.fillMaxSize()) {
        GameHud(
            score = state.score,
            timeLeftSeconds = state.timeLeftSeconds,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        if (state.status == GameStatus.Countdown) {
            Text(
                text = state.countdownSeconds.toString(),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        if (state.status == GameStatus.Finished) {
            Surface(
                modifier = Modifier.align(Alignment.Center),
                shape = MaterialTheme.shapes.large,
                tonalElevation = 6.dp,
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.game_round_over),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = stringResource(R.string.game_score, state.score),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Button(onClick = { onAction(GameAction.OnRestart) }) {
                        Text(stringResource(R.string.game_restart))
                    }
                    Button(onClick = onExit) {
                        Text(stringResource(R.string.game_back_to_menu))
                    }
                }
            }
        }
    }
}
