package com.lumen.bugs_android.data.local

import androidx.room3.Dao
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.PrimaryKey
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "game_result")
data class GameResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long,
    val score: Int,
    val difficulty: String,
    val finishedAt: Long,
)

data class GameRecordRow(
    val id: Long,
    val score: Int,
    val finishedAt: Long,
    val name: String,
    val difficulty: String,
    val zodiac: String?,
)

@Dao
interface GameResultDao {
    @Query(
        """
        SELECT r.id AS id, r.score AS score, r.finishedAt AS finishedAt,
               p.name AS name, r.difficulty AS difficulty, p.zodiac AS zodiac
        FROM game_result r
        JOIN profile p ON p.id = r.profileId
        ORDER BY r.score DESC
        """,
    )
    fun observeRecords(): Flow<List<GameRecordRow>>

    @Insert
    suspend fun insert(result: GameResultEntity): Long
}
