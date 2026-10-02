package com.lumen.bugs_android.data.local

import androidx.room3.Dao
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.PrimaryKey
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val gender: String,
    val course: Int,
    val difficulty: String,
    val birthDate: Long?,
    val zodiac: String?,
)

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile ORDER BY name")
    fun observeAll(): Flow<List<ProfileEntity>>

    @Insert
    suspend fun insert(profile: ProfileEntity): Long
}
