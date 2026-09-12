package com.lumen.bugs_android.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")
private val PastDates = object : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis <= System.currentTimeMillis()
}

@Composable
fun BirthDatePicker(
    modifier: Modifier = Modifier,
    value: Long? = null,
    onSelect: (Long) -> Unit = {},
) {
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }

    Box(modifier) {
        OutlinedTextField(
            value = value?.let { Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).format(DATE_FORMATTER) }.orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Дата рождения") },
            trailingIcon = {
                IconButton(onClick = { isDialogOpen = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Выбрать дату")
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Box(Modifier.matchParentSize().clickable(interactionSource = null, indication = null) { isDialogOpen = true })
    }

    if (isDialogOpen) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = value,
            yearRange = 1900..LocalDate.now().year,
            selectableDates = PastDates,
        )
        DatePickerDialog(
            onDismissRequest = { isDialogOpen = false },
            confirmButton = {
                TextButton(onClick = { state.selectedDateMillis?.let(onSelect); isDialogOpen = false }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { isDialogOpen = false }) { Text("Cancel") } },
        ) { DatePicker(state = state) }
    }
}
