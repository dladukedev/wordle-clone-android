package com.dladukedev.wordle.game.domain

import javax.inject.Inject
import javax.inject.Singleton

interface GetPracticeModeInitialStateUseCase {
    suspend operator fun invoke(): GameState
}

@Singleton
class GetPracticeModeInitialStateUseCaseImpl @Inject constructor(
    private val practiceGameStateRepository: PracticeGameStateRepository
): GetPracticeModeInitialStateUseCase {
    override suspend fun invoke(): GameState {
        return practiceGameStateRepository.getInitialGameState()
    }

}