package com.lumen.bugs_android

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lumen.bugs_android.authors.AuthorsScreenRoot
import com.lumen.bugs_android.register.RegisterScreenRoot
import com.lumen.bugs_android.rules.RulesScreenRoot
import com.lumen.bugs_android.settings.SettingsScreenRoot
import kotlinx.serialization.Serializable


@Serializable
data object RegisterScreen

@Serializable
data object RulesScreen

@Serializable
data object AuthorsScreen

@Serializable
data object SettingsScreen

data class NavigationBarRoutes(
    val route: Any,
    @StringRes val labelRes: Int,
    val icon: ImageVector
)

val navigationBarRoutes = listOf(
    NavigationBarRoutes(RegisterScreen, R.string.tab_register, Icons.Default.AccountCircle),
    NavigationBarRoutes(RulesScreen, R.string.tab_rules, Icons.Default.Info),
    NavigationBarRoutes(AuthorsScreen, R.string.tab_authors, Icons.Default.Person),
    NavigationBarRoutes(SettingsScreen, R.string.tab_settings, Icons.Default.Settings),
)


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry?.destination

            NavigationBar {
                navigationBarRoutes.forEach { item ->
                    val selected = currentDestination?.hierarchy
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
                        icon = { Icon(item.icon, contentDescription = stringResource(item.labelRes)) },
                        label = { Text(stringResource(item.labelRes)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = RegisterScreen,
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<RegisterScreen> { RegisterScreenRoot() }
            composable<RulesScreen> { RulesScreenRoot() }
            composable<AuthorsScreen> { AuthorsScreenRoot() }
            composable<SettingsScreen> { SettingsScreenRoot() }
        }
    }
}