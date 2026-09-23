package com.lumen.bugs_android.game

import androidx.compose.ui.geometry.Offset

data class Bug(
    val id: Long,
    val type: BugType,
    val position: Offset,
    val velocity: Offset,
) {
    fun advance(dt: Float, speedMultiplier: Float): Bug {
        var vx = velocity.x
        var vy = velocity.y
        var x = position.x + vx * dt * speedMultiplier
        var y = position.y + vy * dt * speedMultiplier
        if (x < 0f || x > 1f) {
            vx = -vx
            x = x.coerceIn(0f, 1f)
        }
        if (y < 0f || y > 1f) {
            vy = -vy
            y = y.coerceIn(0f, 1f)
        }
        return copy(position = Offset(x, y), velocity = Offset(vx, vy))
    }
}
