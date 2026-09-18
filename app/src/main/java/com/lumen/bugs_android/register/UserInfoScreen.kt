package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.lumen.bugs_android.util.formatDateUtc

@Composable
fun UserInfoScreen(
    state: RegisterScreenState,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(R.string.user_info_title), style = MaterialTheme.typography.titleLarge)
        Text(state.toInfoText(), modifier = Modifier.fillMaxWidth())
        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.user_info_continue))
        }
    }
}

@Composable
private fun RegisterScreenState.toInfoText(): String = listOf(
    stringResource(R.string.info_name, name),
    stringResource(R.string.info_gender, stringResource(gender.labelRes)),
    stringResource(R.string.info_course, course),
    stringResource(R.string.info_difficulty, stringResource(difficulty.labelRes)),
    stringResource(R.string.info_birth_date, date?.let(::formatDateUtc) ?: stringResource(R.string.value_none)),
).joinToString("\n")
