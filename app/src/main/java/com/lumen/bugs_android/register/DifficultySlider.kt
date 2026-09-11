package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.math.roundToInt

@Composable
fun DifficultySlider(
    modifier: Modifier = Modifier,
    levels: List<String> = listOf("Лёгкая", "Средняя", "Высокая"),
    value: String = "Лёгкая",
    onSelect: (String) -> Unit = {},
) {
    Column(modifier) {
        Text("Уровень сложности")
        Slider(
            value = levels.indexOf(value).coerceAtLeast(0).toFloat(),
            onValueChange = { onSelect(levels[it.roundToInt()]) },
            valueRange = 0f..levels.lastIndex.toFloat(),
            steps = (levels.size - 2).coerceAtLeast(0)
        )
        Text(value)
    }
}
