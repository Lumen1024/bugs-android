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
import androidx.compose.ui.unit.dp
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
        Text("Пользователь зарегистрирован", style = MaterialTheme.typography.titleLarge)
        Text(state.toInfoText(), modifier = Modifier.fillMaxWidth())
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Назад") }
    }
}

private fun RegisterScreenState.toInfoText(): String = """
    ФИО: $name
    Пол: $gender
    Курс: $course
    Сложность: $difficulty
    Дата рождения: ${date?.let { Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).format(DATE_FORMATTER) } ?: "—"}
""".trimIndent()
