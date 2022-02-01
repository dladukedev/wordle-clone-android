package com.dladukedev.wordle.game.domain

interface StatisticsRepository {
    suspend fun saveDailyChallengeResult(offset: Int, state: GameState)
    suspend fun savePracticeModeResult(state: GameState)
    suspend fun loadDailyChallengeStatSummary(offset: Int): SummaryStatistics
    suspend fun loadPracticeModeStatSummary(): SummaryStatistics
}