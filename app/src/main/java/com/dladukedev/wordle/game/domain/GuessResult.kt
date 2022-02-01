package com.dladukedev.wordle.game.domain

import com.dladukedev.wordle.game.state.LetterResult

data class GuessResult(
    val firstLetter: Char,
    val firstLetterResult: LetterResult,

    val secondLetter: Char,
    val secondLetterResult: LetterResult,

    val thirdLetter: Char,
    val thirdLetterResult: LetterResult,

    val fourthLetter: Char,
    val fourthLetterResult: LetterResult,

    val fifthLetter: Char,
    val fifthLetterResult: LetterResult,
) {
    override fun toString(): String {
        return listOf(firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter).joinToString("")
    }

    val isWin: Boolean
        get() {
            return listOf(
                firstLetterResult,
                secondLetterResult,
                thirdLetterResult,
                fourthLetterResult,
                fifthLetterResult,
            ).all { it == LetterResult.CORRECT }
        }
}