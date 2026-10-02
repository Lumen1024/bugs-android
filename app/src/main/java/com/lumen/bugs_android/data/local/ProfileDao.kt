package com.lumen.bugs_android.data.local

import androidx.room3.Dao
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = SINGLE_ROW_ID,
    val name: String,
    val gender: String,
    val course: Int,
    val difficulty: String,
    val birthDate: Long?,
    val zodiac: String?,
) {
    companion object {
        const val SINGLE_ROW_ID = 0
    }
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    fun observe(): Flow<ProfileEntity?>

    @Upsert
    suspend fun upsert(profile: ProfileEntity)

    @Query("DELETE FROM profile")
    suspend fun clear()
}
