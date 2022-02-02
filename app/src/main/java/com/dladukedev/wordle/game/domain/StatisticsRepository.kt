package com.dladukedev.wordle.game.domain

interface StatisticsRepository {
    suspend fun saveDailyChallengeResult(offset: Int, state: GameState)
    suspend fun savePracticeModeResult(state: GameState)
    suspend fun loadDailyChallengeStatSummary(offset: Int): SummaryStatistics
    suspend fun loadPracticeModeStatSummary(): SummaryStatistics

    suspend fun loadPracticeModeStats(): StatisticsModel
    suspend fun loadDailyChallengeStats(offset: Int): StatisticsModel
    suspend fun loadTotalStats(): StatisticsModel
}