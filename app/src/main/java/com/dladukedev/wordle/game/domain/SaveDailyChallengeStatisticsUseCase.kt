package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface SaveDailyChallengeStatisticsUseCase {
    suspend operator fun invoke(date: LocalDate, gameState: GameState)
}

@Singleton
class SaveDailyChallengeStatisticsUseCaseImpl @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
    private val getDailyChallengeDateOffset: GetDailyChallengeDateOffsetUseCase
): SaveDailyChallengeStatisticsUseCase {
    override suspend fun invoke(date: LocalDate, gameState: GameState) {
        val offset = getDailyChallengeDateOffset(date)
        statisticsRepository.saveDailyChallengeResult(offset, gameState)
    }

}