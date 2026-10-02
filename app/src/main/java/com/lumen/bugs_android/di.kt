package com.lumen.bugs_android

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.lumen.bugs_android.data.local.BugsDatabase
import com.lumen.bugs_android.repository.PlayerRepository
import com.lumen.bugs_android.repository.RoomPlayerRepository
import com.lumen.bugs_android.repository.RoomSettingsRepository
import com.lumen.bugs_android.repository.SettingsRepository
import com.lumen.bugs_android.screen.game.GameViewModel
import com.lumen.bugs_android.screen.register.RegisterScreenViewModel
import com.lumen.bugs_android.screen.settings.SettingsScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            BugsDatabase::class.java,
            "bugs.db",
        )
            .setDriver(AndroidSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single { get<BugsDatabase>().playerDao() }
    single { get<BugsDatabase>().settingsDao() }

    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    single<SettingsRepository> { RoomSettingsRepository(get(), get()) }
    single<PlayerRepository> { RoomPlayerRepository(get(), get()) }

    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::SettingsScreenViewModel)
}
