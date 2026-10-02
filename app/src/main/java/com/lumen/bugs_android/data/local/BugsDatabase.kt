package com.lumen.bugs_android.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [PlayerEntity::class, SettingsEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class BugsDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao

    abstract fun settingsDao(): SettingsDao
}
