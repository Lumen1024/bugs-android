package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RegisterScreen(state, viewModel::onAction, modifier)
}

@Composable
fun RegisterScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {

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
            ZodiacImage(state.zodiac, Modifier.size(256.dp))
            state.zodiac?.let {
                Text(stringResource(it.titleRes), style = MaterialTheme.typography.titleMedium)
            }
        }

        if (!state.isInfoShow) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(RegisterScreenAction.OnNameChange(it)) },
                label = { Text(stringResource(R.string.register_name_label)) },
                modifier = Modifier.fillMaxWidth(),
            )
            GenderMenu(
                Modifier.fillMaxWidth(),
                value = state.gender,
                onSelect = { onAction(RegisterScreenAction.OnGenderChange(it)) }
            )
            CourseSelect(
                value = state.course,
                onSelect = { onAction(RegisterScreenAction.OnCourseChange(it)) }
            )
            DifficultySlider(
                value = state.difficulty,
                onSelect = { onAction(RegisterScreenAction.OnDifficultyChange(it)) }
            )
            BirthDatePicker(
                Modifier.fillMaxWidth(),
                value = state.date,
                onSelect = { onAction(RegisterScreenAction.OnDateChange(it)) }
            )

            Button(
                onClick = { onAction(RegisterScreenAction.OnConfirmButtonClick) },
                enabled = state.confirmButtonEnabled,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.register_confirm))
            }
        } else {
            UserInfoScreen(
                state,
                onBack = { onAction(RegisterScreenAction.OnInfoCloseButtonClick) },
                modifier = modifier
            )
        }
    }
}
