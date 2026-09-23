package com.lumen.bugs_android.game

enum class BugType(val points: Int, val speedFactor: Float) {
    Common(points = 1, speedFactor = 1.0f),
    Fast(points = 2, speedFactor = 1.6f),
    Fat(points = 3, speedFactor = 0.7f),
}
