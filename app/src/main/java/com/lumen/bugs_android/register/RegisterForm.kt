package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lumen.bugs_android.Zodiac

@Composable
fun RegisterForm(modifier: Modifier = Modifier) {
    val name = rememberTextFieldState("")
    var gender by remember { mutableStateOf("Мужской") }
    var course by remember { mutableIntStateOf(1) }
    var difficulty by remember { mutableStateOf("Лёгкая") }
    var birthDate by remember { mutableStateOf<Long?>(null) }
    val zodiac: Zodiac? by remember { derivedStateOf { birthDate?.let { Zodiac.fromDate(it) } } }


    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ZodiacImage(zodiac, Modifier.size(256.dp))
            zodiac?.let { Text(zodiac!!.title, style = MaterialTheme.typography.titleMedium) }
        }

        OutlinedTextField(state = name, label = { Text("ФИО") }, modifier = Modifier.fillMaxWidth())
        GenderMenu(Modifier.fillMaxWidth(), value = gender, onSelect = { gender = it })
        CourseSelect(value = course, onSelect = { course = it })
        DifficultySlider(value = difficulty, onSelect = { difficulty = it })

        var isDialogOpen by remember { mutableStateOf(false) }
        Button(onClick = { isDialogOpen = !isDialogOpen }) {
            Text("Выбрать дату")
        }
        if (isDialogOpen)
            DatePickerModal(
                onDateSelected = { birthDate = it },
                onDismiss = { isDialogOpen = false }
            )
    }

}

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}