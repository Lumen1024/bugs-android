package com.lumen.bugs_android.model

import androidx.annotation.StringRes
import com.lumen.bugs_android.R

enum class Difficulty(@StringRes val labelRes: Int) {
    Easy(R.string.difficulty_easy),
    Medium(R.string.difficulty_medium),
    Hard(R.string.difficulty_hard),
}