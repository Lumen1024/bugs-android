package com.lumen.bugs_android.register

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lumen.bugs_android.User
import com.lumen.bugs_android.Zodiac

@Composable
fun ConfirmButton(
    name: String,
    gender: String,
    course: Int,
    difficulty: String,
    birthDate: Long?,
    zodiac: Zodiac?,
    modifier: Modifier = Modifier,
    onConfirm: (User) -> Unit,
) {
    Button(
        onClick = { onConfirm(User(name, gender, course, difficulty, birthDate, zodiac)) },
        enabled = name.isNotBlank() && birthDate != null,
        modifier = modifier,
    ) {
        Text("Подтвердить")
    }
}
