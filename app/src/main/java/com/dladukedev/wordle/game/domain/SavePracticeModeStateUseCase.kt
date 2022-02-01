package com.dladukedev.wordle.game.domain

import javax.inject.Inject
import javax.inject.Singleton

interface SavePracticeModeStateUseCase {
    suspend operator fun invoke(gameState: GameState)
}

@Singleton
class SavePracticeModeStateUseCaseImpl @Inject constructor(
    private val practiceGameStateRepository: PracticeGameStateRepository
): SavePracticeModeStateUseCase {
    override suspend fun invoke(gameState: GameState) {
        practiceGameStateRepository.saveGameState(gameState)
    }

}