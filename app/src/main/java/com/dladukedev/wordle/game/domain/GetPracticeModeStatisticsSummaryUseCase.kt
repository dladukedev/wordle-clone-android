package com.dladukedev.wordle.game.domain

import javax.inject.Inject

interface GetPracticeModeStatisticsSummaryUseCase {
    suspend operator fun invoke(): SummaryStatistics
}

class GetPracticeModeStatisticsSummaryUseCaseImpl @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
): GetPracticeModeStatisticsSummaryUseCase {
    override suspend fun invoke(): SummaryStatistics {
        return statisticsRepository.loadPracticeModeStatSummary()
    }

}