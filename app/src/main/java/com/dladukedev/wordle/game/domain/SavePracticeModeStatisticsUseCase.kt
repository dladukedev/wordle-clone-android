package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface SavePracticeModeStatisticsUseCase {
    suspend operator fun invoke(gameState: GameState)
}

@Singleton
class SavePracticeModeStatisticsUseCaseImpl @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
): SavePracticeModeStatisticsUseCase {
    override suspend fun invoke(gameState: GameState) {
        statisticsRepository.savePracticeModeResult(gameState)
    }

}