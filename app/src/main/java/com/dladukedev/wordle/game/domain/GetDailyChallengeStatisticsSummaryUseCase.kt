package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface GetDailyChallengeStatisticsSummaryUseCase {
    suspend operator fun invoke(date: LocalDate): SummaryStatistics
}

@Singleton
class GetDailyChallengeStatisticsSummaryUseCaseImpl @Inject constructor(
    private val getDailyChallengeDateOffset: GetDailyChallengeDateOffsetUseCase,
    private val statisticsRepository: StatisticsRepository,
): GetDailyChallengeStatisticsSummaryUseCase {
    override suspend fun invoke(date: LocalDate): SummaryStatistics {
        val offset = getDailyChallengeDateOffset(date)
        return statisticsRepository.loadDailyChallengeStatSummary(offset)
    }

}