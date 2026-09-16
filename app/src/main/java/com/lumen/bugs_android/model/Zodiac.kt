package com.lumen.bugs_android.model

import androidx.annotation.StringRes
import com.lumen.bugs_android.R
import com.lumen.bugs_android.util.toLocalDateUtc
import java.time.MonthDay

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
        /** Первый день каждого знака, по возрастанию. Capricorn начинается 22 декабря. */
        private val SIGN_START_DATES: List<Pair<MonthDay, Zodiac>> = listOf(
            MonthDay.of(1, 20) to Aquarius,
            MonthDay.of(2, 19) to Pisces,
            MonthDay.of(3, 21) to Aries,
            MonthDay.of(4, 20) to Taurus,
            MonthDay.of(5, 21) to Gemini,
            MonthDay.of(6, 21) to Cancer,
            MonthDay.of(7, 23) to Leo,
            MonthDay.of(8, 23) to Virgo,
            MonthDay.of(9, 23) to Libra,
            MonthDay.of(10, 23) to Scorpion,
            MonthDay.of(11, 22) to Sagittarius,
            MonthDay.of(12, 22) to Capricorn,
        )

        fun fromDate(date: Long): Zodiac {
            val monthDay = MonthDay.from(toLocalDateUtc(date))
            return SIGN_START_DATES
                .lastOrNull { (startDate, _) -> monthDay >= startDate }
                ?.second
                ?: Capricorn
        }
    }
}
