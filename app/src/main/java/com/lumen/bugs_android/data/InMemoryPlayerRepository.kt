package com.lumen.bugs_android.data

import com.lumen.bugs_android.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryPlayerRepository : PlayerRepository {
    private val _player = MutableStateFlow<Player?>(null)
    override val player: StateFlow<Player?> = _player.asStateFlow()

    override suspend fun setPlayer(player: Player): Result<Unit> = runCatching {
        require(player.name.isNotBlank()) { "Player name must not be blank" }
        _player.value = player
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        _player.value = null
    }
}
