package com.dladukedev.wordle.game.domain

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface GetDateForDailyChallengeUseCase {
    operator fun invoke(): LocalDate
}

class GetDateForDailyChallengeUseCaseImpl @Inject constructor(): GetDateForDailyChallengeUseCase {
    override fun invoke(): LocalDate {
        return LocalDate.now()
    }
}