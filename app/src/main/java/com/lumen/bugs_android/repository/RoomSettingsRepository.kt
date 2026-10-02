package com.lumen.bugs_android.repository

import com.lumen.bugs_android.data.local.SettingsDao
import com.lumen.bugs_android.data.local.SettingsEntity
import com.lumen.bugs_android.model.GameSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoomSettingsRepository(
    private val settingsDao: SettingsDao,
    scope: CoroutineScope,
) : SettingsRepository {
    override val settings: StateFlow<GameSettings> = settingsDao.observe()
        .map { it?.toModel() ?: GameSettings() }
        .stateIn(scope, SharingStarted.Eagerly, GameSettings())

    override suspend fun update(transform: (GameSettings) -> GameSettings) {
        settingsDao.upsert(transform(settings.value).toEntity())
    }
}

private fun SettingsEntity.toModel(): GameSettings = GameSettings(
    gameSpeed = gameSpeed,
    maxBugsCount = maxBugsCount,
    bonusIntervalSeconds = bonusIntervalSeconds,
    roundDurationSeconds = roundDurationSeconds,
)

private fun GameSettings.toEntity(): SettingsEntity = SettingsEntity(
    gameSpeed = gameSpeed,
    maxBugsCount = maxBugsCount,
    bonusIntervalSeconds = bonusIntervalSeconds,
    roundDurationSeconds = roundDurationSeconds,
)
