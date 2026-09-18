package com.lumen.bugs_android.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun GameScreenRoot(
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameScreen(onExit, modifier)
}

@Composable
fun GameScreen(
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.game_placeholder),
            style = MaterialTheme.typography.titleLarge,
        )
        Button(
            onClick = onExit,
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text(stringResource(R.string.game_back_to_menu))
        }
    }
}
