package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

interface GetDailyChallengeDateOffsetUseCase {
    operator fun invoke(date: LocalDate): Int
}

@Singleton
class GetDailyChallengeDateOffsetUseCaseImpl @Inject constructor(): GetDailyChallengeDateOffsetUseCase {
    // TODO: How to handle this?
    private val startDate = LocalDate.of(2021, 6, 19)

    override fun invoke(date: LocalDate): Int {
        require(date >= startDate)

        return ChronoUnit.DAYS.between(startDate, date).toInt()
    }
}