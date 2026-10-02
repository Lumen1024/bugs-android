package com.lumen.bugs_android.repository

import com.lumen.bugs_android.data.local.ProfileDao
import com.lumen.bugs_android.data.local.SessionDao
import com.lumen.bugs_android.data.local.SessionEntity
import com.lumen.bugs_android.model.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class RoomCurrentProfileRepository(
    private val sessionDao: SessionDao,
    private val profileDao: ProfileDao,
    scope: CoroutineScope,
) : CurrentProfileRepository {
    override val currentProfile: StateFlow<Profile?> =
        combine(sessionDao.observe(), profileDao.observeAll()) { session, profiles ->
            val id = session?.profileId
            profiles.firstOrNull { it.id == id }?.toModel()
        }.stateIn(scope, SharingStarted.Eagerly, null)

    override suspend fun selectProfile(profile: Profile) {
        sessionDao.upsert(SessionEntity(profileId = profile.id))
    }

    override suspend fun clear() {
        sessionDao.upsert(SessionEntity(profileId = null))
    }
}
