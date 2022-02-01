package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface GetDailyChallengeGameNameUseCase {
    operator fun invoke(date: LocalDate): String
}

@Singleton
class GetDailyChallengeGameNameUseCaseImpl @Inject constructor(
    private val getDailyChallengeDateOffset: GetDailyChallengeDateOffsetUseCase
): GetDailyChallengeGameNameUseCase {
    override fun invoke(date: LocalDate): String {
        return getDailyChallengeDateOffset(date).toString()
    }
}