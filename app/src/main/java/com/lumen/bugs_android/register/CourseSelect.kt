package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lumen.bugs_android.R

@Composable
fun CourseSelect(
    modifier: Modifier = Modifier,
    maxValue: Int = 4,
    value: Int = 1,
    onSelect: (Int) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier) {
        Button(onClick = { expanded = !expanded }) {
            Text(stringResource(R.string.course_format, value))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (1..maxValue).forEach {
                DropdownMenuItem(
                    text = { Text(it.toString()) },
                    onClick = { onSelect(it); expanded = false }
                )
            }
        }
    }
}