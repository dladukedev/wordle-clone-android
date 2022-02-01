package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface GetDailyChallengeWordInitialState {
    suspend operator fun invoke(date: LocalDate): GameState
}

@Singleton
class GetDailyChallengeWordInitialStateImpl @Inject constructor(
    private val getDailyChallengeDateOffset: GetDailyChallengeDateOffsetUseCase,
    private val dailyChallengeGameStateRepository: DailyChallengeGameStateRepository,
): GetDailyChallengeWordInitialState {
    override suspend fun invoke(date: LocalDate): GameState {
        val offset = getDailyChallengeDateOffset(date)

        return dailyChallengeGameStateRepository.getInitialGameState(offset)
    }

}