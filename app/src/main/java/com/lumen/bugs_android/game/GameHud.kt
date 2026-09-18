package com.lumen.bugs_android.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lumen.bugs_android.R
import java.util.Locale

@Composable
fun GameHud(
    score: Int,
    timeLeftSeconds: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.game_score, score),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = formatTime(timeLeftSeconds),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

private fun formatTime(seconds: Int): String =
    String.format(Locale.US, "%02d:%02d", seconds / 60, seconds % 60)
