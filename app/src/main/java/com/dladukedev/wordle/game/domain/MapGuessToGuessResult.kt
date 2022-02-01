package com.dladukedev.wordle.game.domain

import com.dladukedev.wordle.game.state.LetterResult

object MapGuessToGuessResult {
    operator fun invoke(guess: String, targetWord: String): GuessResult {
        val target = targetWord.uppercase()
        val result = mutableMapOf<Int, Pair<Char, LetterResult>>()

        //First Pass
        target.toList().forEachIndexed { index, letter ->
            if (letter == guess[index]) {
                result[index] = Pair(letter, LetterResult.CORRECT)
            }
        }

        // Second Pass
        target.toList().forEachIndexed { targetIndex, letter ->
            if (guess.contains(letter)) {
                for (index in guess.indices) {
                    val guessLetter = guess[index]
                    if (guessLetter == letter && result[index] == null && result[targetIndex] != Pair(
                            letter,
                            LetterResult.CORRECT
                        )
                    ) {
                        result[index] = Pair(guessLetter, LetterResult.WRONG_LOCATION)
                        break
                    }
                }
            }
        }

        // Final Pass
        guess.indices.forEach { index ->
            if (result[index] == null) {
                result[index] = Pair(guess[index], LetterResult.INCORRECT)
            }
        }

        return GuessResult(
            guess[0],
            result[0]!!.second,
            guess[1],
            result[1]!!.second,
            guess[2],
            result[2]!!.second,
            guess[3],
            result[3]!!.second,
            guess[4],
            result[4]!!.second,
        )
    }
}
