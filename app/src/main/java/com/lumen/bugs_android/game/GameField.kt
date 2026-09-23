package com.lumen.bugs_android.game

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

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

    BoxWithConstraints(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures { tap ->
                if (!currentEnabled) return@detectTapGestures
                val bugPx = BUG_SIZE.toPx()
                val movableWidth = (size.width - bugPx).coerceAtLeast(0f)
                val movableHeight = (size.height - bugPx).coerceAtLeast(0f)
                val hit = currentBugs.firstOrNull { bug ->
                    val left = bug.position.x * movableWidth
                    val top = bug.position.y * movableHeight
                    tap.x in left..(left + bugPx) && tap.y in top..(top + bugPx)
                }
                if (hit != null) onBugHit(hit.id) else onMiss()
            }
        },
    ) {
        val movableWidth = (maxWidth - BUG_SIZE).coerceAtLeast(0.dp)
        val movableHeight = (maxHeight - BUG_SIZE).coerceAtLeast(0.dp)
        bugs.forEach { bug ->
            Icon(
                imageVector = Icons.Default.BugReport,
                contentDescription = null,
                modifier = Modifier
                    .offset(
                        x = movableWidth * bug.position.x,
                        y = movableHeight * bug.position.y,
                    )
                    .size(BUG_SIZE),
            )
        }
    }
}
