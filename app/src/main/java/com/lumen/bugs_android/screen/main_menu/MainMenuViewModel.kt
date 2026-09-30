package com.lumen.bugs_android.screen.main_menu

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.model.Player
import com.lumen.bugs_android.repository.PlayerRepository
import kotlinx.coroutines.flow.StateFlow

class MainMenuViewModel(
    playerRepository: PlayerRepository,
) : ViewModel() {
    val player: StateFlow<Player?> = playerRepository.player
}
