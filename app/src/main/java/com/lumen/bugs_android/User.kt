package com.lumen.bugs_android

data class User(
    val name: String,
    val gender: String,
    val course: Int,
    val difficulty: String,
    val birthDate: Long?,
    val zodiac: Zodiac?,
)