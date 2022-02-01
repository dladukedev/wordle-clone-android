package com.dladukedev.wordle.game.domain

import javax.inject.Inject

interface GetPracticeModeGameNameUseCase {
    operator fun invoke(): String
}

class GetPracticeModeGameNameUseCaseImpl @Inject constructor(): GetPracticeModeGameNameUseCase {
    override fun invoke(): String {
        return "Practice"
    }

}