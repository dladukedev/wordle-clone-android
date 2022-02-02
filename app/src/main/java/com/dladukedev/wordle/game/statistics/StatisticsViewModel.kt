package com.dladukedev.wordle.game.statistics

import androidx.lifecycle.ViewModel
import com.dladukedev.wordle.game.domain.GetStatsScreenDataUseCase
import com.dladukedev.wordle.game.domain.StatsScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    getStatsScreenData: GetStatsScreenDataUseCase
): ViewModel() {
    val stats: Flow<StatsScreenModel> = flow {
       emit(getStatsScreenData())
    }
}