package com.lumen.bugs_android.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lumen.bugs_android.screen.authors.AuthorsScreenRoot
import com.lumen.bugs_android.screen.game.GameScreenRoot
import com.lumen.bugs_android.screen.register.RegisterScreenRoot
import com.lumen.bugs_android.screen.rules.RulesScreenRoot
import com.lumen.bugs_android.screen.settings.SettingsScreenRoot

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RegisterRoute,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable<RegisterRoute> {
            RegisterScreenRoot(
                onContinue = {
                    navController.navigate(GameRoute) {
                        popUpTo(RegisterRoute) { inclusive = true }
                    }
                },
            )
        }
        composable<GameRoute> {
            GameScreenRoot(onExit = { navController.popBackStack() })
        }
        composable<RulesRoute> { RulesScreenRoot() }
        composable<AuthorsRoute> { AuthorsScreenRoot() }
        composable<SettingsRoute> { SettingsScreenRoot() }
    }
}
