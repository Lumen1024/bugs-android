package com.lumen.bugs_android.model

import java.time.Instant
import java.time.ZoneOffset

enum class Zodiac(val title: String, val link: String) {
    Aries("Овен", "https://www.zodiack.ru/images/female/aries.png"),
    Taurus("Телец", "https://www.zodiack.ru/images/female/taurus.png"),
    Gemini("Близнецы", "https://www.zodiack.ru/images/female/gemini.png"),
    Cancer("Рак", "https://www.zodiack.ru/images/boss/cancer.png"),
    Leo("Лев", "https://www.zodiack.ru/images/female/leo.png"),
    Virgo("Дева", "https://www.zodiack.ru/images/female/virgo.png"),
    Libra("Весы", "https://www.zodiack.ru/images/female/libra.png"),
    Scorpion("Скорпион", "https://www.zodiack.ru/images/female/scorpio.png"),
    Sagittarius("Стрелец", "https://www.zodiack.ru/images/female/sagittarius.png"),
    Capricorn("Козерог", "https://www.zodiack.ru/images/female/capricorn.png"),
    Aquarius("Водолей", "https://www.zodiack.ru/images/female/aquarius.png"),
    Pisces("Рыбы", "https://www.zodiack.ru/images/female/pisces.png");
    companion object {
        fun fromDate(date: Long): Zodiac {
            val localDate = Instant.ofEpochMilli(date)
                .atZone(ZoneOffset.UTC)
                .toLocalDate()

            val month = localDate.monthValue
            val day = localDate.dayOfMonth

            return when (month) {
                1 -> if (day < 20) Capricorn else Aquarius
                2 -> if (day < 19) Aquarius else Pisces
                3 -> if (day < 21) Pisces else Aries
                4 -> if (day < 20) Aries else Taurus
                5 -> if (day < 21) Taurus else Gemini
                6 -> if (day < 21) Gemini else Cancer
                7 -> if (day < 23) Cancer else Leo
                8 -> if (day < 23) Leo else Virgo
                9 -> if (day < 23) Virgo else Libra
                10 -> if (day < 23) Libra else Scorpion
                11 -> if (day < 22) Scorpion else Sagittarius
                else -> if (day < 22) Sagittarius else Capricorn
            }
        }
    }
}