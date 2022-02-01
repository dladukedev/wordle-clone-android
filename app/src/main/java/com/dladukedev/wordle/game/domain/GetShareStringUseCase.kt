package com.dladukedev.wordle.game.domain

import com.dladukedev.wordle.game.state.LetterResult
import javax.inject.Inject
import javax.inject.Singleton

interface GetShareStringUseCase {
    operator fun invoke(
        state: GameState,
        gameName: String,
        isDarkMode: Boolean,
        isColorBlindMode: Boolean
    ): String
}

@Singleton
class GetShareStringUseCaseImpl @Inject constructor() : GetShareStringUseCase {
    override fun invoke(
        state: GameState,
        gameName: String,
        isDarkMode: Boolean,
        isColorBlindMode: Boolean
    ): String {
        val correctBlock = if (isColorBlindMode) "\uD83D\uDFE7" else "\uD83D\uDFE9"
        val wrongLocationBlock = if (isColorBlindMode) "\uD83D\uDFE6" else "\uD83D\uDFE8"
        val incorrectBlock = if (isDarkMode) "⬛" else "⬜"

        val guessCount = if (state.isWin) state.previousGuesses.size else "X"

        val header = "Wordle $gameName $guessCount/6"

        val guessStrings = state.previousGuesses.map {
            listOf(
                it.firstLetterResult,
                it.secondLetterResult,
                it.thirdLetterResult,
                it.fourthLetterResult,
                it.fifthLetterResult
            ).joinToString("") { letterResult ->
                when (letterResult) {
                    LetterResult.CORRECT -> correctBlock
                    LetterResult.WRONG_LOCATION -> wrongLocationBlock
                    LetterResult.INCORRECT -> incorrectBlock
                }
            }
        }

        return buildString {
            appendLine(header)
            appendLine()
            for (line in guessStrings) {
                appendLine(line)
            }
        }
    }
}