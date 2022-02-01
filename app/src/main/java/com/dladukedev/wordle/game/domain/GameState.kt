package com.dladukedev.wordle.game.domain

data class GameState(
    val targetWord: String,
    val currentInput: String = "",
    val previousGuesses: List<GuessResult> = emptyList(),
) {

    val isComplete: Boolean
        get() = isWin || isLoss

    val isWin: Boolean
        get() {
            return previousGuesses.isNotEmpty() && previousGuesses.last().isWin
        }

    val guessCount: Int get() = previousGuesses.size

    val isLoss: Boolean
        get() {
            return guessCount == 6 && previousGuesses.last().isWin.not()
        }
}