package com.lumen.bugs_android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.lumen.bugs_android.R

enum class BottomTab(
    val labelRes: Int,
    val icon: ImageVector,
) {
    Auth(R.string.nav_auth, Icons.Filled.Person),
    Authors(R.string.nav_authors, Icons.Filled.Groups),
    Game(R.string.nav_game, Icons.Filled.SportsEsports),
    Rules(R.string.nav_rules, Icons.AutoMirrored.Filled.MenuBook),
    Settings(R.string.nav_settings, Icons.Filled.Settings),
}

@Composable
fun AppBottomBar(
    selectedTab: BottomTab,
    onSelectTab: (BottomTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        BottomTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selectedTab,
                onClick = { onSelectTab(tab) },
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(stringResource(tab.labelRes), fontSize = 12.sp) },
            )
        }
    }
}
