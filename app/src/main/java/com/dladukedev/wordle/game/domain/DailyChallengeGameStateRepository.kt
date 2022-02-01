package com.dladukedev.wordle.game.domain

interface DailyChallengeGameStateRepository {
    suspend fun getInitialGameState(offset: Int): GameState
    suspend fun saveGameState(offset: Int, state: GameState)
}