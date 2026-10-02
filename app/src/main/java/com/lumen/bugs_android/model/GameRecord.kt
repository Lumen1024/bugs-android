package com.lumen.bugs_android.model

data class GameRecord(
    val id: Long,
    val name: String,
    val difficulty: Difficulty,
    val zodiac: Zodiac?,
    val score: Int,
    val finishedAt: Long,
)
