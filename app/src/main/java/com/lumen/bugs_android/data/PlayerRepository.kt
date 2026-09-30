package com.lumen.bugs_android.data

import com.lumen.bugs_android.model.Player
import kotlinx.coroutines.flow.StateFlow

interface PlayerRepository {
    val player: StateFlow<Player?>

    suspend fun setPlayer(player: Player): Result<Unit>

    suspend fun clear(): Result<Unit>
}
