package com.lumen.bugs_android.screen.create_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.bugs_android.model.Difficulty
import com.lumen.bugs_android.model.Gender
import com.lumen.bugs_android.model.Profile
import com.lumen.bugs_android.model.Zodiac
import com.lumen.bugs_android.repository.CurrentProfileRepository
import com.lumen.bugs_android.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateProfileState(
    val name: String = "",
    val gender: Gender = Gender.Male,
    val course: Int = 1,
    val difficulty: Difficulty = Difficulty.Medium,
    val date: Long? = null,
    val zodiac: Zodiac? = null,
    val isInfoShow: Boolean = false
) {
    val confirmButtonEnabled: Boolean
        get() = name.isNotBlank() && date != null
}

sealed class CreateProfileAction {
    data class OnNameChange(val name: String) : CreateProfileAction()
    data class OnGenderChange(val gender: Gender) : CreateProfileAction()
    data class OnCourseChange(val course: Int) : CreateProfileAction()
    data class OnDifficultyChange(val difficulty: Difficulty) : CreateProfileAction()
    data class OnDateChange(val date: Long) : CreateProfileAction()
    data object OnConfirmButtonClick : CreateProfileAction()
    data object OnContinueButtonClick : CreateProfileAction()
}

class CreateProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val currentProfileRepository: CurrentProfileRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CreateProfileState())
    val state = _state.asStateFlow()

    private var createdProfile: Profile? = null

    fun reset() {
        createdProfile = null
        _state.value = CreateProfileState()
    }

    fun onAction(action: CreateProfileAction) {
        when (action) {
            is CreateProfileAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            is CreateProfileAction.OnGenderChange -> _state.update { it.copy(gender = action.gender) }
            is CreateProfileAction.OnCourseChange -> _state.update { it.copy(course = action.course) }
            is CreateProfileAction.OnDateChange ->
                _state.update { it.copy(date = action.date, zodiac = Zodiac.fromDate(action.date)) }
            is CreateProfileAction.OnDifficultyChange -> _state.update { it.copy(difficulty = action.difficulty) }
            CreateProfileAction.OnConfirmButtonClick -> viewModelScope.launch {
                val current = _state.value
                val profile = Profile(
                    name = current.name,
                    gender = current.gender,
                    course = current.course,
                    difficulty = current.difficulty,
                    birthDate = current.date,
                    zodiac = current.zodiac,
                )
                profileRepository.createProfile(profile).onSuccess { created ->
                    createdProfile = created
                    _state.update { it.copy(isInfoShow = true) }
                }
            }
            CreateProfileAction.OnContinueButtonClick -> viewModelScope.launch {
                createdProfile?.let { currentProfileRepository.selectProfile(it) }
            }
        }
    }
}
