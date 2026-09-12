package com.lumen.bugs_android.model

import androidx.annotation.StringRes
import com.lumen.bugs_android.R

enum class Gender(@StringRes val labelRes: Int) {
    Male(R.string.gender_male),
    Female(R.string.gender_female),
    Helicopter(R.string.gender_helicopter),
}