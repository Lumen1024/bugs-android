package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lumen.bugs_android.R
import com.lumen.bugs_android.model.Difficulty
import kotlin.math.roundToInt

@Composable
fun DifficultySlider(
    modifier: Modifier = Modifier,
    levels: List<Difficulty> = Difficulty.entries,
    value: Difficulty = Difficulty.Medium,
    onSelect: (Difficulty) -> Unit = {},
) {
    Column(modifier) {
        Text(stringResource(R.string.difficulty_label))
        Slider(
            value = levels.indexOf(value).coerceAtLeast(0).toFloat(),
            onValueChange = { onSelect(levels[it.roundToInt()]) },
            valueRange = 0f..levels.lastIndex.toFloat(),
            steps = (levels.size - 2).coerceAtLeast(0)
        )
        Text(stringResource(value.labelRes))
    }
}
