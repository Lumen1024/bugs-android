package com.lumen.bugs_android.screen.game

import androidx.compose.ui.graphics.Color

enum class BugType(val points: Int, val speedFactor: Float, val color: Color) {
    Common(points = 1, speedFactor = 1.0f, color = Color(0xFF4E342E)),
    Fast(points = 2, speedFactor = 1.6f, color = Color(0xFF0277BD)),
    Fat(points = 3, speedFactor = 0.7f, color = Color(0xFF6A1B9A)),
}
