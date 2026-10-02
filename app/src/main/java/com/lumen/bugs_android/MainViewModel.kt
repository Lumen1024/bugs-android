package com.lumen.bugs_android

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.model.Profile
import com.lumen.bugs_android.repository.CurrentProfileRepository
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    currentProfileRepository: CurrentProfileRepository,
) : ViewModel() {
    val currentProfile: StateFlow<Profile?> = currentProfileRepository.currentProfile
}
