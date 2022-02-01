package com.dladukedev.wordle.game.domain

interface PracticeGameStateRepository {
    suspend fun getInitialGameState(): GameState
    suspend fun saveGameState(state: GameState)
}