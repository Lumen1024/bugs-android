package com.lumen.bugs_android.screen.game

import androidx.compose.ui.graphics.Color
import com.lumen.bugs_android.R

enum class BugType(val points: Int, val speedFactor: Float, val color: Color, val labelRes: Int) {
    Common(points = 1, speedFactor = 1.0f, color = Color(0xFF4E342E), labelRes = R.string.bug_common),
    Fast(points = 2, speedFactor = 1.6f, color = Color(0xFF0277BD), labelRes = R.string.bug_fast),
    Fat(points = 3, speedFactor = 0.7f, color = Color(0xFF6A1B9A), labelRes = R.string.bug_fat),
}
