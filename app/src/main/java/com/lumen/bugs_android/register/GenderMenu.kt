package com.lumen.bugs_android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun GenderMenu(
    modifier: Modifier = Modifier,
    genders: List<String> = listOf("Мужской", "Женский", "Вертолет"),
    value: String = "Мужской",
    onSelect: (String) -> Unit = {},
) {
    Row(modifier.selectableGroup(), horizontalArrangement = Arrangement.SpaceAround) {
        genders.forEach { text ->
            Row(
                Modifier.padding(vertical = 4.dp).selectable(
                        selected = (text == value),
                        onClick = { onSelect(text) },
                        role = Role.RadioButton
                    ),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                RadioButton(
                    selected = (text == value),
                    onClick = null
                )
                Text(text = text)
            }
        }
    }
}