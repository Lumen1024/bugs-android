package com.lumen.bugs_android.repository

import com.lumen.bugs_android.data.local.ProfileDao
import com.lumen.bugs_android.data.local.ProfileEntity
import com.lumen.bugs_android.model.Difficulty
import com.lumen.bugs_android.model.Gender
import com.lumen.bugs_android.model.Profile
import com.lumen.bugs_android.model.Zodiac
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoomProfileRepository(
    private val profileDao: ProfileDao,
    scope: CoroutineScope,
) : ProfileRepository {
    override val profile: StateFlow<Profile?> = profileDao.observe()
        .map { it?.toModel() }
        .stateIn(scope, SharingStarted.Eagerly, null)

    override suspend fun setProfile(profile: Profile): Result<Unit> = runCatching {
        require(profile.name.isNotBlank()) { "Profile name must not be blank" }
        profileDao.upsert(profile.toEntity())
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        profileDao.clear()
    }
}

private fun ProfileEntity.toModel(): Profile = Profile(
    name = name,
    gender = Gender.valueOf(gender),
    course = course,
    difficulty = Difficulty.valueOf(difficulty),
    birthDate = birthDate,
    zodiac = zodiac?.let(Zodiac::valueOf),
)

private fun Profile.toEntity(): ProfileEntity = ProfileEntity(
    name = name,
    gender = gender.name,
    course = course,
    difficulty = difficulty.name,
    birthDate = birthDate,
    zodiac = zodiac?.name,
)
