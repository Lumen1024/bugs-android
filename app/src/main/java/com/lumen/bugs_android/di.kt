package com.lumen.bugs_android

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.lumen.bugs_android.data.local.BugsDatabase
import com.lumen.bugs_android.data.local.MIGRATION_1_2
import com.lumen.bugs_android.data.local.MIGRATION_2_3
import com.lumen.bugs_android.repository.GameResultRepository
import com.lumen.bugs_android.repository.CurrentProfileRepository
import com.lumen.bugs_android.repository.ProfileRepository
import com.lumen.bugs_android.repository.RoomCurrentProfileRepository
import com.lumen.bugs_android.repository.RoomGameResultRepository
import com.lumen.bugs_android.repository.RoomProfileRepository
import com.lumen.bugs_android.repository.RoomSettingsRepository
import com.lumen.bugs_android.repository.SettingsRepository
import com.lumen.bugs_android.screen.create_profile.CreateProfileViewModel
import com.lumen.bugs_android.screen.game.GameViewModel
import com.lumen.bugs_android.screen.profile.ProfileViewModel
import com.lumen.bugs_android.screen.records.RecordsViewModel
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
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }
    single { get<BugsDatabase>().profileDao() }
    single { get<BugsDatabase>().settingsDao() }
    single { get<BugsDatabase>().sessionDao() }

    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    single<SettingsRepository> { RoomSettingsRepository(get(), get()) }
    single<ProfileRepository> { RoomProfileRepository(get(), get()) }
    single<CurrentProfileRepository> { RoomCurrentProfileRepository(get(), get(), get()) }
    single { get<BugsDatabase>().gameResultDao() }
    single<GameResultRepository> { RoomGameResultRepository(get(), get()) }

    viewModelOf(::CreateProfileViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::RecordsViewModel)
    viewModelOf(::SettingsScreenViewModel)
}
