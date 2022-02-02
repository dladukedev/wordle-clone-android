package com.dladukedev.wordle.game.data

import com.dladukedev.wordle.game.domain.DailyChallengeGameStateRepository
import com.dladukedev.wordle.game.domain.GameState
import com.dladukedev.wordle.game.domain.MapGuessToGuessResult
import javax.inject.Inject
import javax.inject.Singleton

fun mapDataStoreGameStateToGameState(dataStoreGameState: DataStoreGameState): GameState {
    val target = dataStoreGameState.targetWord
    val guesses = dataStoreGameState.guesses.map { guess -> MapGuessToGuessResult(guess, target) }

    return GameState(
        target,
        previousGuesses = guesses
    )
}

fun mapGameStateToDataStoreGameState(gameState: GameState): DataStoreGameState {
    val target = gameState.targetWord
    val guesses = gameState.previousGuesses.map { it.toString() }

    return DataStoreGameState(
        target,
        guesses,
    )
}

@Singleton
class DailyChallengeGameStateRepositoryImpl @Inject constructor(
    private val dataStoreWrapper: SaveDataStore,
    private val dailyChallengeBank: DailyChallengeBank,
): DailyChallengeGameStateRepository {
    override suspend fun getInitialGameState(offset: Int): GameState {
        val savedData = dataStoreWrapper.getDailyChallengeSavedData()

        return if(savedData == null || savedData.dateOffset != offset) {
            dataStoreWrapper.clearDailyChallengeSavedData()

            val dailyChallengeTargetWord = dailyChallengeBank.getAnswer(offset)
            GameState(dailyChallengeTargetWord)
        } else {
            mapDataStoreGameStateToGameState(savedData.gameState)
        }
    }

    override suspend fun saveGameState(offset: Int, state: GameState) {
        val dataStoreGameState = mapGameStateToDataStoreGameState(state)
        dataStoreWrapper.saveDailyChallengeData(offset, dataStoreGameState)
    }
}