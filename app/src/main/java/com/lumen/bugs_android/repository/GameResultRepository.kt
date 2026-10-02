package com.lumen.bugs_android.repository

import com.lumen.bugs_android.model.Difficulty
import com.lumen.bugs_android.model.GameRecord
import kotlinx.coroutines.flow.StateFlow

interface GameResultRepository {
    val records: StateFlow<List<GameRecord>>

    suspend fun saveResult(profileId: Long, score: Int, difficulty: Difficulty): Result<Unit>
}
