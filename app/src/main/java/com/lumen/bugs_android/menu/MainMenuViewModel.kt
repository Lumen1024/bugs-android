package com.lumen.bugs_android.menu

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.data.PlayerRepository
import com.lumen.bugs_android.model.Player
import kotlinx.coroutines.flow.StateFlow

class MainMenuViewModel(
    playerRepository: PlayerRepository,
) : ViewModel() {
    val player: StateFlow<Player?> = playerRepository.player
}
