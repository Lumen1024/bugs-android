package com.lumen.bugs_android.screen.game

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private val BUG_SIZE = 56.dp

@Composable
fun GameField(
    bugs: List<Bug>,
    enabled: Boolean,
    onBugHit: (Long) -> Unit,
    onMiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentBugs by rememberUpdatedState(bugs)
    val currentEnabled by rememberUpdatedState(enabled)
    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier) {
        val area = FieldArea(
            widthPx = constraints.maxWidth.toFloat(),
            heightPx = constraints.maxHeight.toFloat(),
            bugSize = with(density) { BUG_SIZE.toPx() },
        )

        bugs.forEach { bug -> BugIcon(bug, area) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { tap ->
                        if (!currentEnabled) return@detectTapGestures
                        val hit = area.tryHit(currentBugs, tap)
                        if (hit != null) onBugHit(hit.id) else onMiss()
                    }
                },
        )
    }
}

@Composable
private fun BugIcon(bug: Bug, area: FieldArea) {
    val topLeft = area.topLeft(bug)
    Icon(
        imageVector = Icons.Default.BugReport,
        contentDescription = null,
        tint = bug.type.color,
        modifier = Modifier
            .offset { IntOffset(topLeft.x.roundToInt(), topLeft.y.roundToInt()) }
            .size(BUG_SIZE),
    )
}

private class FieldArea(
    widthPx: Float,
    heightPx: Float,
    val bugSize: Float,
) {
    private val movableWidth = (widthPx - bugSize).coerceAtLeast(0f)
    private val movableHeight = (heightPx - bugSize).coerceAtLeast(0f)

    fun topLeft(bug: Bug): Offset =
        Offset(bug.position.x * movableWidth, bug.position.y * movableHeight)

    fun tryHit(bugs: List<Bug>, tap: Offset): Bug? = bugs.firstOrNull { bug ->
        val topLeft = topLeft(bug)
        tap.x in topLeft.x..(topLeft.x + bugSize) &&
            tap.y in topLeft.y..(topLeft.y + bugSize)
    }
}
