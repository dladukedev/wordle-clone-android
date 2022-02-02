package com.dladukedev.wordle.game.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

interface GetStatsScreenDataUseCase {
    suspend operator fun invoke(): StatsScreenModel
}

@Singleton
class GetStatsScreenDataUseCaseImpl @Inject constructor(
    private val getDateForDailyChallenge: GetDateForDailyChallengeUseCase,
    private val getDailyChallengeDateOffset: GetDailyChallengeDateOffsetUseCase,
    private val statisticsRepository: StatisticsRepository,
) : GetStatsScreenDataUseCase {
    override suspend fun invoke(): StatsScreenModel {
        val totalStatsAsync = coroutineScope {
            async { statisticsRepository.loadTotalStats() }
        }
        val dailyStatsAsync = coroutineScope {
            val offset = getDailyChallengeDateOffset(getDateForDailyChallenge())
            async { statisticsRepository.loadDailyChallengeStats(offset) }
        }
        val practiceStatsAsync = coroutineScope {
            async { statisticsRepository.loadPracticeModeStats() }
        }

        val (totalStats, dailyStats, practiceStats) = awaitAll(
            totalStatsAsync,
            dailyStatsAsync,
            practiceStatsAsync
        )

        return StatsScreenModel(
            totalStats,
            dailyStats,
            practiceStats
        )
    }
}