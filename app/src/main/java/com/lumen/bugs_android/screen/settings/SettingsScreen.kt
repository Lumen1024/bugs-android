package com.lumen.bugs_android.screen.settings

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
import com.lumen.bugs_android.model.GameSettings
import com.lumen.bugs_android.model.GameSettingsSpecs
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SettingsScreenViewModel = koinViewModel(),
) {
    val settings by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        settings = settings,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun SettingsScreen(
    settings: GameSettings,
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
            value = settings.gameSpeed,
            valueText = { String.format(Locale.US, "%.1f×", it) },
            onValueChange = { onAction(SettingsScreenAction.OnGameSpeedChange(it)) },
            spec = GameSettingsSpecs.gameSpeed,
        )
        SettingSlider(
            label = stringResource(R.string.setting_max_bugs),
            value = settings.maxBugsCount.toFloat(),
            valueText = { String.format(Locale.US, "%d шт", it.roundToInt()) },
            onValueChange = { onAction(SettingsScreenAction.OnMaxBugsCountChange(it.roundToInt())) },
            spec = GameSettingsSpecs.maxBugsCount,
        )
        SettingSlider(
            label = stringResource(R.string.setting_bonus_interval),
            value = settings.bonusIntervalSeconds.toFloat(),
            valueText = { String.format(Locale.US, "%d с", it.roundToInt()) },
            onValueChange = { onAction(SettingsScreenAction.OnBonusIntervalChange(it.roundToInt())) },
            spec = GameSettingsSpecs.bonusIntervalSeconds,
        )
        SettingSlider(
            label = stringResource(R.string.setting_round_duration),
            value = settings.roundDurationSeconds.toFloat(),
            valueText = { String.format(Locale.US, "%d с", it.roundToInt()) },
            onValueChange = { onAction(SettingsScreenAction.OnRoundDurationChange(it.roundToInt())) },
            spec = GameSettingsSpecs.roundDurationSeconds,
        )
    }
}
