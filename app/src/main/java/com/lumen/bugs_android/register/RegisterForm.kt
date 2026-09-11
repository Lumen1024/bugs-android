package com.lumen.bugs_android.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumen.bugs_android.Zodiac
import com.lumen.bugs_android.getZodiacFromDate

@Composable
fun RegisterForm(modifier: Modifier = Modifier) {
    val name = rememberTextFieldState("")
    var expanded by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    Column(modifier.fillMaxSize()) {
        OutlinedTextField(state = name,label={ Text("ФИО") })
        GenderMenu()
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
        Column {
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it }
            )
            Text(text = sliderPosition.toString())
        }
        Button(onClick = {isDialogOpen= !isDialogOpen}) {
            Text("Выбрать дату")
        }
        if (isDialogOpen)
            DatePickerModal(
                onDateSelected = { selectedDateMillis = it },
                onDismiss = { isDialogOpen = false }
            )
        selectedDateMillis?.let { millis ->
            ZodiacImageBox(
                zodiac = getZodiacFromDate(millis),
                modifier = Modifier.padding(16.dp)
            )
        }
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

@Composable
fun ZodiacImageBox(
    zodiac: Zodiac,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = zodiac.symbol,
                fontSize = 64.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = zodiac.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}