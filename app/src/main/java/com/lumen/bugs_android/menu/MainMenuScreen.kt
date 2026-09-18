package com.lumen.bugs_android.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.R
import com.lumen.bugs_android.model.Player
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainMenuScreenRoot(
    onPlay: () -> Unit,
    onRules: () -> Unit,
    onAuthors: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainMenuViewModel = koinViewModel(),
) {
    val player by viewModel.player.collectAsStateWithLifecycle()
    MainMenuScreen(player, onPlay, onRules, onAuthors, onSettings, modifier)
}

@Composable
fun MainMenuScreen(
    player: Player?,
    onPlay: () -> Unit,
    onRules: () -> Unit,
    onAuthors: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.menu_title),
            style = MaterialTheme.typography.displaySmall,
        )
        Text(
            text = player?.name ?: stringResource(R.string.menu_guest),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MenuButton(stringResource(R.string.menu_play), onPlay)
            MenuButton(stringResource(R.string.menu_rules), onRules)
            MenuButton(stringResource(R.string.menu_authors), onAuthors)
            MenuButton(stringResource(R.string.menu_settings), onSettings)
        }
    }
}

@Composable
private fun MenuButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text)
    }
}
