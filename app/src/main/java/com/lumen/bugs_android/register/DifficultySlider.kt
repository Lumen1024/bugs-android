package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.math.roundToInt

@Composable
fun DifficultySlider() {
    val levels = listOf("Лёгкая", "Средняя", "Высокая")
    var index by remember { mutableIntStateOf(0) }
    Column {
        Text("Уровень сложности")
        Slider(
            value = index.toFloat(),
            onValueChange = { index = it.roundToInt() },
            valueRange = 0f..2f,
            steps = 1
        )
        Text(levels[index])
    }
}
