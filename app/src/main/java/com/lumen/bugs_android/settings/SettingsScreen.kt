package com.lumen.bugs_android.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

private val GAME_SPEED_RANGE = 0.5f..1.5f
private const val GAME_SPEED_STEP = 0.1f
private val GAME_SPEED_STEPS = ((GAME_SPEED_RANGE.endInclusive - GAME_SPEED_RANGE.start) / GAME_SPEED_STEP - 1).toInt()

@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SettingsScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun SettingsScreen(
    state: SettingsScreenState,
    onAction: (SettingsScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Slider(
            value = state.gameSpeed,
            onValueChange = {onAction(SettingsScreenAction.OnGameSpeedChange(it))},
            valueRange = GAME_SPEED_RANGE,
            steps = GAME_SPEED_STEPS
        )
    }
}
