package com.lumen.bugs_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lumen.bugs_android.authors.AuthorsScreenRoot
import com.lumen.bugs_android.register.RegisterScreenRoot
import com.lumen.bugs_android.rules.RulesScreenRoot
import com.lumen.bugs_android.settings.SettingsScreenRoot

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RegisterRoute,
        modifier = modifier,
    ) {
        composable<RegisterRoute> { RegisterScreenRoot() }
        composable<RulesRoute> { RulesScreenRoot() }
        composable<AuthorsRoute> { AuthorsScreenRoot() }
        composable<SettingsRoute> { SettingsScreenRoot() }
    }
}
