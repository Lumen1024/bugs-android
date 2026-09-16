package com.lumen.bugs_android.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.R
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import kotlin.math.roundToInt

private val GAME_SPEED_RANGE = 0.5f..2.0f
private const val GAME_SPEED_STEP = 0.1f
private val GAME_SPEED_STEPS = sliderSteps(GAME_SPEED_RANGE, GAME_SPEED_STEP)

private val MAX_BUGS_RANGE = 1f..50f
private val MAX_BUGS_STEPS = sliderSteps(MAX_BUGS_RANGE)

private val BONUS_INTERVAL_RANGE = 1f..30f
private val BONUS_INTERVAL_STEPS = sliderSteps(BONUS_INTERVAL_RANGE)

private val ROUND_DURATION_RANGE = 15f..300f
private const val ROUND_DURATION_STEP = 5f
private val ROUND_DURATION_STEPS = sliderSteps(ROUND_DURATION_RANGE, ROUND_DURATION_STEP)

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
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        SettingSlider(
            label = stringResource(R.string.setting_game_speed),
            valueText = String.format(Locale.US, "%.1f×", state.gameSpeed),
            value = state.gameSpeed,
            onValueChange = { onAction(SettingsScreenAction.OnGameSpeedChange(it)) },
            valueRange = GAME_SPEED_RANGE,
            steps = GAME_SPEED_STEPS,
        )
        SettingSlider(
            label = stringResource(R.string.setting_max_bugs),
            valueText = String.format(Locale.US, "%d шт", state.maxBugsCount),
            value = state.maxBugsCount.toFloat(),
            onValueChange = { onAction(SettingsScreenAction.OnMaxBugsCountChange(it.roundToInt())) },
            valueRange = MAX_BUGS_RANGE,
            steps = MAX_BUGS_STEPS,
        )
        SettingSlider(
            label = stringResource(R.string.setting_bonus_interval),
            valueText = String.format(Locale.US, "%d с", state.bonusAppearanceInterval),
            value = state.bonusAppearanceInterval.toFloat(),
            onValueChange = { onAction(SettingsScreenAction.OnBonusIntervalChange(it.roundToInt())) },
            valueRange = BONUS_INTERVAL_RANGE,
            steps = BONUS_INTERVAL_STEPS,
        )
        SettingSlider(
            label = stringResource(R.string.setting_round_duration),
            valueText = String.format(Locale.US, "%d с", state.roundDurationSeconds),
            value = state.roundDurationSeconds.toFloat(),
            onValueChange = { onAction(SettingsScreenAction.OnRoundDurationChange(it.roundToInt())) },
            valueRange = ROUND_DURATION_RANGE,
            steps = ROUND_DURATION_STEPS,
        )
    }
}
