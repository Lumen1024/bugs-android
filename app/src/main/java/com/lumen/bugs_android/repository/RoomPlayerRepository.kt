package com.lumen.bugs_android.repository

import com.lumen.bugs_android.data.local.PlayerDao
import com.lumen.bugs_android.data.local.PlayerEntity
import com.lumen.bugs_android.model.Difficulty
import com.lumen.bugs_android.model.Gender
import com.lumen.bugs_android.model.Player
import com.lumen.bugs_android.model.Zodiac
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoomPlayerRepository(
    private val playerDao: PlayerDao,
    scope: CoroutineScope,
) : PlayerRepository {
    override val player: StateFlow<Player?> = playerDao.observe()
        .map { it?.toModel() }
        .stateIn(scope, SharingStarted.Eagerly, null)

    override suspend fun setPlayer(player: Player): Result<Unit> = runCatching {
        require(player.name.isNotBlank()) { "Player name must not be blank" }
        playerDao.upsert(player.toEntity())
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        playerDao.clear()
    }
}

private fun PlayerEntity.toModel(): Player = Player(
    name = name,
    gender = Gender.valueOf(gender),
    course = course,
    difficulty = Difficulty.valueOf(difficulty),
    birthDate = birthDate,
    zodiac = zodiac?.let(Zodiac::valueOf),
)

private fun Player.toEntity(): PlayerEntity = PlayerEntity(
    name = name,
    gender = gender.name,
    course = course,
    difficulty = difficulty.name,
    birthDate = birthDate,
    zodiac = zodiac?.name,
)
