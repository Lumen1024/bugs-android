package com.lumen.bugs_android.repository

import com.lumen.bugs_android.model.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val profile: StateFlow<Profile?>

    suspend fun setProfile(profile: Profile): Result<Unit>

    suspend fun clear(): Result<Unit>
}
