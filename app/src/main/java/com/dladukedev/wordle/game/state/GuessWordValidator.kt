package com.dladukedev.wordle.game.state

import com.dladukedev.wordle.game.data.WordBank
import javax.inject.Inject
import javax.inject.Singleton

enum class GuessValidationResult {
    Valid,
    TooShort,
    InvalidWord,
}

interface GuessWordValidator {
    fun validateGuess(guess: String): GuessValidationResult
}

@Singleton
class GuessWordValidatorImpl @Inject constructor(private val wordBank: WordBank): GuessWordValidator {
    private fun isTooShort(guess: String): Boolean = guess.length < 5
    private fun isNotInWordBank(guess: String): Boolean = wordBank.isWordInBank(guess.lowercase()).not()

    override fun validateGuess(guess: String): GuessValidationResult {
        return when {
            isTooShort(guess) -> GuessValidationResult.TooShort
            isNotInWordBank(guess) -> GuessValidationResult.InvalidWord
            else -> GuessValidationResult.Valid
        }
    }
}