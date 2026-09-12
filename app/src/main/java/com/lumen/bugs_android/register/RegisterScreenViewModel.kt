package com.lumen.bugs_android.register

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.Zodiac
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterScreenState(
    val name: String = "",
    val gender: String = "Мужской",
    val course: Int = 1,
    val difficulty: String = "Средняя",
    val date: Long? = null,
    val zodiac: Zodiac? = null,
    val confirmButtonEnabled: Boolean = false,
    val isInfoShow: Boolean = false
)

sealed class RegisterScreenAction {
    data class OnNameChange(val name: String) : RegisterScreenAction()
    data class OnGenderChange(val gender: String) : RegisterScreenAction()
    data class OnCourseChange(val course: Int) : RegisterScreenAction()
    data class OnDifficultyChange(val difficulty: String) : RegisterScreenAction()
    data class OnDateChange(val date: Long): RegisterScreenAction()
    data object OnConfirmButtonClick : RegisterScreenAction()
    data object OnInfoCloseButtonClick : RegisterScreenAction()
}

class RegisterScreenViewModel : ViewModel(){
    private val _state = MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: RegisterScreenAction) {
        when (action) {
            is RegisterScreenAction.OnNameChange -> {
                _state.update { it.copy(name = action.name) }
                updateConfirmButtonState()
            }
            is RegisterScreenAction.OnGenderChange -> _state.update { it.copy(gender = action.gender) }
            is RegisterScreenAction.OnCourseChange -> _state.update { it.copy(course = action.course) }
            is RegisterScreenAction.OnDateChange -> {
                _state.update { it.copy(date = action.date, zodiac = Zodiac.fromDate(action.date)) }
                updateConfirmButtonState()
            }
            is RegisterScreenAction.OnDifficultyChange -> _state.update { it.copy(difficulty = action.difficulty) }
            RegisterScreenAction.OnConfirmButtonClick -> _state.update { it.copy(isInfoShow = true) }
            RegisterScreenAction.OnInfoCloseButtonClick -> _state.update { it.copy(isInfoShow = false) }
        }
    }

    private fun updateConfirmButtonState() {
        _state.update { it.copy(confirmButtonEnabled = it.name.isNotBlank() && it.date != null) }
    }
}