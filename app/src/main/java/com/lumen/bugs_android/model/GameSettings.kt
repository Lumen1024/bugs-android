package com.lumen.bugs_android.model

import kotlin.math.roundToInt

data class GameSettings(
    val gameSpeed: Float = GameSettingsSpecs.gameSpeed.default,
    val maxBugsCount: Int = GameSettingsSpecs.maxBugsCount.default.roundToInt(),
    val bonusIntervalSeconds: Int = GameSettingsSpecs.bonusIntervalSeconds.default.roundToInt(),
    val roundDurationSeconds: Int = GameSettingsSpecs.roundDurationSeconds.default.roundToInt(),
)

object GameSettingsSpecs {
    val gameSpeed = GameSettingSpec(range = 0.5f..2f, step = 0.1f, default = 1f)
    val maxBugsCount = GameSettingSpec(range = 1f..50f, step = 1f, default = 10f)
    val bonusIntervalSeconds = GameSettingSpec(range = 1f..30f, step = 1f, default = 10f)
    val roundDurationSeconds = GameSettingSpec(range = 15f..300f, step = 5f, default = 60f)
}

data class GameSettingSpec(
    val range: ClosedFloatingPointRange<Float>,
    val step: Float,
    val default: Float,
) {
    val valueCount: Int get() = ((range.endInclusive - range.start) / step).roundToInt() + 1
}
