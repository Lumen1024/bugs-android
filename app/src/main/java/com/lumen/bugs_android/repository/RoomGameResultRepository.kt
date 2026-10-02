package com.lumen.bugs_android.repository

import com.lumen.bugs_android.data.local.GameRecordRow
import com.lumen.bugs_android.data.local.GameResultDao
import com.lumen.bugs_android.data.local.GameResultEntity
import com.lumen.bugs_android.model.Difficulty
import com.lumen.bugs_android.model.GameRecord
import com.lumen.bugs_android.model.Zodiac
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoomGameResultRepository(
    private val gameResultDao: GameResultDao,
    scope: CoroutineScope,
) : GameResultRepository {
    override val records: StateFlow<List<GameRecord>> = gameResultDao.observeRecords()
        .map { rows -> rows.map { it.toModel() } }
        .stateIn(scope, SharingStarted.Eagerly, emptyList())

    override suspend fun saveResult(profileId: Long, score: Int): Result<Unit> = runCatching {
        gameResultDao.insert(
            GameResultEntity(
                profileId = profileId,
                score = score,
                finishedAt = System.currentTimeMillis(),
            ),
        )
    }
}

private fun GameRecordRow.toModel(): GameRecord = GameRecord(
    id = id,
    name = name,
    difficulty = Difficulty.valueOf(difficulty),
    zodiac = zodiac?.let(Zodiac::valueOf),
    score = score,
    finishedAt = finishedAt,
)
