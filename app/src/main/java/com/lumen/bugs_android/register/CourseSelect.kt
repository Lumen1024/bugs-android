package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp

@Composable
fun CourseSelect() {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Button(onClick ={ expanded = !expanded } ) {
            Text("Курс")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("1") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("2") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("3") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("4") },
                onClick = { /* Do something... */ }
            )
        }
    }
}