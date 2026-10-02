package com.lumen.bugs_android.data.local

import androidx.room3.Dao
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Int = SINGLE_ROW_ID,
    val profileId: Long?,
) {
    companion object {
        const val SINGLE_ROW_ID = 0
    }
}

@Dao
interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    fun observe(): Flow<SessionEntity?>

    @Upsert
    suspend fun upsert(session: SessionEntity)
}
