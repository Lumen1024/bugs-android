package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lumen.bugs_android.R
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")

@Composable
fun UserInfoScreen(
    state: RegisterScreenState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(R.string.user_info_title), style = MaterialTheme.typography.titleLarge)
        Text(state.toInfoText(), modifier = Modifier.fillMaxWidth())
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.user_info_back))
        }
    }
}

@Composable
private fun RegisterScreenState.toInfoText(): String = listOf(
    stringResource(R.string.info_name, name),
    stringResource(R.string.info_gender, stringResource(gender.labelRes)),
    stringResource(R.string.info_course, course),
    stringResource(R.string.info_difficulty, stringResource(difficulty.labelRes)),
    stringResource(R.string.info_birth_date, date?.let(::formatDate) ?: stringResource(R.string.value_none)),
).joinToString("\n")

private fun formatDate(millis: Long): String =
    Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).format(DATE_FORMATTER)
