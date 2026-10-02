package com.lumen.bugs_android.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.bugs_android.model.Profile
import com.lumen.bugs_android.repository.CurrentProfileRepository
import com.lumen.bugs_android.repository.ProfileRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val currentProfileRepository: CurrentProfileRepository,
) : ViewModel() {
    val profiles: StateFlow<List<Profile>> = profileRepository.profiles

    fun onProfileSelected(profile: Profile) {
        viewModelScope.launch {
            currentProfileRepository.selectProfile(profile)
        }
    }
}
