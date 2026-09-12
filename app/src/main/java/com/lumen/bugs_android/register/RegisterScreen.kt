package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.lumen.bugs_android.User
import com.lumen.bugs_android.Zodiac
import kotlin.text.isNotBlank

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    val name = rememberTextFieldState("")
    var gender by remember { mutableStateOf("Мужской") }
    var course by remember { mutableIntStateOf(1) }
    var difficulty by remember { mutableStateOf("Лёгкая") }
    var birthDate: Long? by remember { mutableStateOf(null) }
    val zodiac: Zodiac? by remember { derivedStateOf { birthDate?.let { Zodiac.fromDate(it) } } }
    val user: User? by remember {
        derivedStateOf {
            if (birthDate != null && name.text.toString().isNotBlank())
                User(name.text.toString(), gender, course, difficulty, birthDate, zodiac)
            else
                null
        }
    }
    var isInfoOpen by remember { mutableStateOf(false) }

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
            zodiac?.let { Text(it.title, style = MaterialTheme.typography.titleMedium) }
        }

        if (!isInfoOpen) {
            OutlinedTextField(
                state = name,
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )
            GenderMenu(Modifier.fillMaxWidth(), value = gender, onSelect = { gender = it })
            CourseSelect(value = course, onSelect = { course = it })
            DifficultySlider(value = difficulty, onSelect = { difficulty = it })
            BirthDatePicker(Modifier.fillMaxWidth(), value = birthDate, onSelect = { birthDate = it })

            Button(
                onClick = { isInfoOpen = true},
                enabled = user != null,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Подтвердить")
            }
        } else {
            UserInfoScreen(user!!, onBack = { isInfoOpen = false }, modifier = modifier)
        }
    }
}
