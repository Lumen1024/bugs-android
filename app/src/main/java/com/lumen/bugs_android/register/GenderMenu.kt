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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.lumen.bugs_android.model.Gender

@Composable
fun GenderMenu(
    modifier: Modifier = Modifier,
    genders: List<Gender> = Gender.entries,
    value: Gender = Gender.Male,
    onSelect: (Gender) -> Unit = {},
) {
    Row(modifier.selectableGroup(), horizontalArrangement = Arrangement.SpaceAround) {
        genders.forEach { gender ->
            Row(
                Modifier.padding(vertical = 4.dp).selectable(
                        selected = (gender == value),
                        onClick = { onSelect(gender) },
                        role = Role.RadioButton
                    ),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                RadioButton(
                    selected = (gender == value),
                    onClick = null
                )
                Text(text = stringResource(gender.labelRes))
            }
        }
    }
}
