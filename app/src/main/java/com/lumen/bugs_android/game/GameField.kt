package com.lumen.bugs_android.game

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val BUG_SIZE = 56.dp

@Composable
fun GameField(
    bugs: List<Bug>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
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
