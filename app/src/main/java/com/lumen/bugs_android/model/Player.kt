package com.lumen.bugs_android.model

data class Player(
    val name: String,
    val gender: Gender,
    val course: Int,
    val difficulty: Difficulty,
    val birthDate: Long?,
    val zodiac: Zodiac?,
)
