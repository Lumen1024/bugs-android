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
import androidx.compose.ui.res.stringResource
import com.lumen.bugs_android.R
import com.lumen.bugs_android.util.formatDateUtc
import java.time.LocalDate

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
            value = value?.let(::formatDateUtc).orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.date_birth_label)) },
            trailingIcon = {
                IconButton(onClick = { isDialogOpen = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.date_pick))
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
                TextButton(onClick = { state.selectedDateMillis?.let(onSelect); isDialogOpen = false }) { Text(stringResource(R.string.date_ok)) }
            },
            dismissButton = { TextButton(onClick = { isDialogOpen = false }) { Text(stringResource(R.string.date_cancel)) } },
        ) { DatePicker(state = state) }
    }
}
