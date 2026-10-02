package com.lumen.bugs_android.repository

import com.lumen.bugs_android.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryProfileRepository : ProfileRepository {
    private val _profile = MutableStateFlow<Profile?>(null)
    override val profile: StateFlow<Profile?> = _profile.asStateFlow()

    override suspend fun setProfile(profile: Profile): Result<Unit> = runCatching {
        require(profile.name.isNotBlank()) { "Profile name must not be blank" }
        _profile.value = profile
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        _profile.value = null
    }
}
