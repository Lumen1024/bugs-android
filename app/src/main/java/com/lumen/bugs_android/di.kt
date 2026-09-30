package com.lumen.bugs_android

import com.lumen.bugs_android.repository.InMemoryPlayerRepository
import com.lumen.bugs_android.repository.InMemorySettingsRepository
import com.lumen.bugs_android.repository.PlayerRepository
import com.lumen.bugs_android.repository.SettingsRepository
import com.lumen.bugs_android.screen.game.GameViewModel
import com.lumen.bugs_android.screen.main_menu.MainMenuViewModel
import com.lumen.bugs_android.screen.register.RegisterScreenViewModel
import com.lumen.bugs_android.screen.settings.SettingsScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<SettingsRepository> { InMemorySettingsRepository() }
    single<PlayerRepository> { InMemoryPlayerRepository() }

    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::MainMenuViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::SettingsScreenViewModel)
}
