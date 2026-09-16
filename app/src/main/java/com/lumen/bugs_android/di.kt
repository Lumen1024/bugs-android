package com.lumen.bugs_android

import com.lumen.bugs_android.register.RegisterScreenViewModel
import com.lumen.bugs_android.settings.SettingsScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}