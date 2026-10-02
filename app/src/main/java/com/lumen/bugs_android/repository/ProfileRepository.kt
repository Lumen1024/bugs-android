package com.lumen.bugs_android.repository

import com.lumen.bugs_android.model.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val profiles: StateFlow<List<Profile>>

    suspend fun createProfile(profile: Profile): Result<Profile>
}
