package com.lumen.bugs_android.screen.create_profile

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.R
import com.lumen.bugs_android.model.Zodiac
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateProfileScreenRoot(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.reset() }
    CreateProfileScreen(
        state = state,
        onAction = viewModel::onAction,
        onContinue = {
            viewModel.onAction(CreateProfileAction.OnContinueButtonClick)
            onContinue()
        },
        modifier = modifier,
    )
}

@Composable
fun CreateProfileScreen(
    state: CreateProfileState,
    onAction: (CreateProfileAction) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ZodiacHeader(state.zodiac)

        if (state.isInfoShow) {
            UserInfoScreen(
                state = state,
                onContinue = onContinue,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            CreateProfileForm(
                state = state,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ZodiacHeader(zodiac: Zodiac?) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ZodiacImage(zodiac, Modifier.size(200.dp))
        zodiac?.let {
            Text(stringResource(it.titleRes), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun CreateProfileForm(
    state: CreateProfileState,
    onAction: (CreateProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onAction(CreateProfileAction.OnNameChange(it)) },
            label = { Text(stringResource(R.string.register_name_label)) },
            modifier = Modifier.fillMaxWidth(),
        )
        GenderMenu(
            Modifier.fillMaxWidth(),
            value = state.gender,
            onSelect = { onAction(CreateProfileAction.OnGenderChange(it)) }
        )
        CourseSelect(
            value = state.course,
            onSelect = { onAction(CreateProfileAction.OnCourseChange(it)) }
        )
        DifficultySlider(
            value = state.difficulty,
            onSelect = { onAction(CreateProfileAction.OnDifficultyChange(it)) }
        )
        BirthDatePicker(
            Modifier.fillMaxWidth(),
            value = state.date,
            onSelect = { onAction(CreateProfileAction.OnDateChange(it)) }
        )

        Button(
            onClick = { onAction(CreateProfileAction.OnConfirmButtonClick) },
            enabled = state.confirmButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.register_confirm))
        }
    }
}
