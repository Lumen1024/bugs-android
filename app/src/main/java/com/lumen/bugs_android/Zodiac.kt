package com.lumen.bugs_android

import java.time.Instant
import java.time.ZoneOffset

enum class Zodiac(val title: String, val symbol: String) {
    aries("Овен", "♈"),
    taurus("Телец", "♉"),
    gemini("Близнецы", "♊"),
    cancer("Рак", "♋"),
    leo("Лев", "♌"),
    virgo("Дева", "♍"),
    libra("Весы", "♎"),
    scorpion("Скорпион", "♏"),
    sagittarius("Стрелец", "♐"),
    capricorn("Козерог", "♑"),
    aquarius("Водолей", "♒"),
    pisces("Рыбы", "♓")
}

fun getZodiacFromDate(date: Long): Zodiac {
    val localDate = Instant.ofEpochMilli(date)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

    val month = localDate.monthValue
    val day = localDate.dayOfMonth

    return when (month) {
        1 -> if (day < 20) Zodiac.capricorn else Zodiac.aquarius
        2 -> if (day < 19) Zodiac.aquarius else Zodiac.pisces
        3 -> if (day < 21) Zodiac.pisces else Zodiac.aries
        4 -> if (day < 20) Zodiac.aries else Zodiac.taurus
        5 -> if (day < 21) Zodiac.taurus else Zodiac.gemini
        6 -> if (day < 21) Zodiac.gemini else Zodiac.cancer
        7 -> if (day < 23) Zodiac.cancer else Zodiac.leo
        8 -> if (day < 23) Zodiac.leo else Zodiac.virgo
        9 -> if (day < 23) Zodiac.virgo else Zodiac.libra
        10 -> if (day < 23) Zodiac.libra else Zodiac.scorpion
        11 -> if (day < 22) Zodiac.scorpion else Zodiac.sagittarius
        else -> if (day < 22) Zodiac.sagittarius else Zodiac.capricorn
    }
}
