package com.lumen.bugs_android.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [ProfileEntity::class, SettingsEntity::class, GameResultEntity::class, SessionEntity::class],
    version = 3,
    exportSchema = false,
)
abstract class BugsDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    abstract fun settingsDao(): SettingsDao

    abstract fun gameResultDao(): GameResultDao

    abstract fun sessionDao(): SessionDao
}
