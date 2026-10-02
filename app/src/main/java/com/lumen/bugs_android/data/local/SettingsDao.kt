package com.lumen.bugs_android.data.local

import androidx.room3.Dao
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = SINGLE_ROW_ID,
    val gameSpeed: Float,
    val maxBugsCount: Int,
    val bonusIntervalSeconds: Int,
    val roundDurationSeconds: Int,
) {
    companion object {
        const val SINGLE_ROW_ID = 0
    }
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings LIMIT 1")
    fun observe(): Flow<SettingsEntity?>

    @Upsert
    suspend fun upsert(settings: SettingsEntity)

    @Query("DELETE FROM settings")
    suspend fun clear()
}
