package com.lumen.bugs_android.model

import androidx.annotation.StringRes
import com.lumen.bugs_android.R
import java.time.Instant
import java.time.ZoneOffset

enum class Zodiac(@StringRes val titleRes: Int, val link: String) {
    Aries(R.string.zodiac_aries, "https://www.zodiack.ru/images/female/aries.png"),
    Taurus(R.string.zodiac_taurus, "https://www.zodiack.ru/images/female/taurus.png"),
    Gemini(R.string.zodiac_gemini, "https://www.zodiack.ru/images/female/gemini.png"),
    Cancer(R.string.zodiac_cancer, "https://www.zodiack.ru/images/boss/cancer.png"),
    Leo(R.string.zodiac_leo, "https://www.zodiack.ru/images/female/leo.png"),
    Virgo(R.string.zodiac_virgo, "https://www.zodiack.ru/images/female/virgo.png"),
    Libra(R.string.zodiac_libra, "https://www.zodiack.ru/images/female/libra.png"),
    Scorpion(R.string.zodiac_scorpion, "https://www.zodiack.ru/images/female/scorpio.png"),
    Sagittarius(R.string.zodiac_sagittarius, "https://www.zodiack.ru/images/female/sagittarius.png"),
    Capricorn(R.string.zodiac_capricorn, "https://www.zodiack.ru/images/female/capricorn.png"),
    Aquarius(R.string.zodiac_aquarius, "https://www.zodiack.ru/images/female/aquarius.png"),
    Pisces(R.string.zodiac_pisces, "https://www.zodiack.ru/images/female/pisces.png");

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
