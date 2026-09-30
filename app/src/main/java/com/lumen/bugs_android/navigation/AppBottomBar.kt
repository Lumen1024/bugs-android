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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lumen.bugs_android.R

data class BottomNavItem(
    val route: Any,
    val labelRes: Int,
    val icon: ImageVector,
)

private val bottomNavItems: List<BottomNavItem> = listOf(
    BottomNavItem(RegisterRoute, R.string.nav_auth, Icons.Filled.Person),
    BottomNavItem(AuthorsRoute, R.string.nav_authors, Icons.Filled.Groups),
    BottomNavItem(GameRoute, R.string.nav_game, Icons.Filled.SportsEsports),
    BottomNavItem(RulesRoute, R.string.nav_rules, Icons.AutoMirrored.Filled.MenuBook),
    BottomNavItem(SettingsRoute, R.string.nav_settings, Icons.Filled.Settings),
)

@Composable
fun AppBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar(modifier = modifier) {
        bottomNavItems.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.hasRoute(item.route::class) } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(stringResource(item.labelRes), fontSize = 12.sp) },
            )
        }
    }
}
