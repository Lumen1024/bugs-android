package com.lumen.bugs_android.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.lumen.bugs_android.R
import kotlinx.serialization.Serializable

@Serializable
data object RegisterRoute

@Serializable
data object RulesRoute

@Serializable
data object AuthorsRoute

@Serializable
data object SettingsRoute

data class NavigationBarRoute(
    val route: Any,
    @StringRes val labelRes: Int,
    val icon: ImageVector,
)

val navigationBarRoutes = listOf(
    NavigationBarRoute(RegisterRoute, R.string.tab_register, Icons.Default.AccountCircle),
    NavigationBarRoute(RulesRoute, R.string.tab_rules, Icons.Default.Info),
    NavigationBarRoute(AuthorsRoute, R.string.tab_authors, Icons.Default.Person),
    NavigationBarRoute(SettingsRoute, R.string.tab_settings, Icons.Default.Settings),
)
