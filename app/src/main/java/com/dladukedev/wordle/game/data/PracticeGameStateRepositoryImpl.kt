package com.dladukedev.wordle.game.data

import com.dladukedev.wordle.game.domain.GameState
import com.dladukedev.wordle.game.domain.PracticeGameStateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeGameStateRepositoryImpl @Inject constructor(
    private val dataStoreWrapper: SaveDataStore,
    private val wordBank: WordBank,
) : PracticeGameStateRepository {
    override suspend fun getInitialGameState(): GameState {
        val savedState = dataStoreWrapper.getPracticeSavedData()

        val gameState = savedState?.let {
            mapDataStoreGameStateToGameState(it)
        }

        return if (gameState == null || gameState.isComplete) {
            dataStoreWrapper.clearPracticeSavedData()

            val practiceTargetWord = wordBank.getRandomWord()
            GameState(practiceTargetWord)
        } else {
            gameState
        }
    }

    override suspend fun saveGameState(state: GameState) {
        val dataStoreGameState = mapGameStateToDataStoreGameState(state)
        dataStoreWrapper.savePracticeData(dataStoreGameState)
    }
}