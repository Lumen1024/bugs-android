package com.lumen.bugs_android.screen.records

import androidx.lifecycle.ViewModel
import com.lumen.bugs_android.model.GameRecord
import com.lumen.bugs_android.repository.GameResultRepository
import kotlinx.coroutines.flow.StateFlow

class RecordsViewModel(
    gameResultRepository: GameResultRepository,
) : ViewModel() {
    val records: StateFlow<List<GameRecord>> = gameResultRepository.records
}
