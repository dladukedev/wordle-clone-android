package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface SaveDailyChallengeStateUseCase {
    suspend operator fun invoke(date: LocalDate, gameState: GameState)
}

@Singleton
class SaveDailyChallengeStateUseCaseImpl @Inject constructor(
    private val getDailyChallengeDateOffset: GetDailyChallengeDateOffsetUseCase,
    private val dailyChallengeGameStateRepository: DailyChallengeGameStateRepository,
): SaveDailyChallengeStateUseCase {
    override suspend fun invoke(date: LocalDate, gameState: GameState) {
        val offset = getDailyChallengeDateOffset(date)
        dailyChallengeGameStateRepository.saveGameState(offset, gameState)
    }

}