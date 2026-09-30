package com.lumen.bugs_android

import com.lumen.bugs_android.data.PlayerRepository
import com.lumen.bugs_android.data.SettingsRepository
import com.lumen.bugs_android.screen.game.GameViewModel
import com.lumen.bugs_android.screen.main_menu.MainMenuViewModel
import com.lumen.bugs_android.screen.register.RegisterScreenViewModel
import com.lumen.bugs_android.screen.settings.SettingsScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::SettingsRepository)
    singleOf(::PlayerRepository)

    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::MainMenuViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::SettingsScreenViewModel)
}
