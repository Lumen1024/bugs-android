package com.lumen.bugs_android.data.local

import androidx.room3.migration.Migration
import androidx.sqlite.execSQL

val MIGRATION_1_2 = Migration(1, 2) { connection ->
    connection.execSQL(
        "CREATE TABLE IF NOT EXISTS `session` " +
            "(`id` INTEGER NOT NULL, `profileId` INTEGER, PRIMARY KEY(`id`))",
    )
}

val MIGRATION_2_3 = Migration(2, 3) { connection ->
    connection.execSQL(
        "ALTER TABLE `game_result` ADD COLUMN `difficulty` TEXT NOT NULL DEFAULT 'Medium'",
    )
}
