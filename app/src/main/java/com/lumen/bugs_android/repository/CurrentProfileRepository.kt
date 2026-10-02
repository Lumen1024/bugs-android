package com.lumen.bugs_android.repository

import com.lumen.bugs_android.model.Profile
import kotlinx.coroutines.flow.StateFlow

interface CurrentProfileRepository {
    val currentProfile: StateFlow<Profile?>

    suspend fun selectProfile(profile: Profile)

    suspend fun clear()
}
