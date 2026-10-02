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
    override val profiles: StateFlow<List<Profile>> = profileDao.observeAll()
        .map { list -> list.map { it.toModel() } }
        .stateIn(scope, SharingStarted.Eagerly, emptyList())

    override suspend fun createProfile(profile: Profile): Result<Profile> = runCatching {
        require(profile.name.isNotBlank()) { "Profile name must not be blank" }
        val id = profileDao.insert(profile.toEntity())
        profile.copy(id = id)
    }
}

private fun ProfileEntity.toModel(): Profile = Profile(
    id = id,
    name = name,
    gender = Gender.valueOf(gender),
    course = course,
    difficulty = Difficulty.valueOf(difficulty),
    birthDate = birthDate,
    zodiac = zodiac?.let(Zodiac::valueOf),
)

private fun Profile.toEntity(): ProfileEntity = ProfileEntity(
    id = id,
    name = name,
    gender = gender.name,
    course = course,
    difficulty = difficulty.name,
    birthDate = birthDate,
    zodiac = zodiac?.name,
)
