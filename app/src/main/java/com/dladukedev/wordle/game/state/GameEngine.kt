package com.dladukedev.wordle.game.state

import com.dladukedev.wordle.game.domain.GameState
import com.dladukedev.wordle.game.domain.GuessResult
import com.dladukedev.wordle.game.domain.MapGuessToGuessResult

object GameEngine {
    private const val MAX_LENGTH = 5

    sealed class Action {
        object SubmitGuess : Action()
        data class AddLetter(val letter: Char) : Action()
        object RemoveLetter : Action()
    }

    fun reduce(state: GameState, action: Action): GameState {
        return when (action) {
            is Action.AddLetter -> reduceAddLetterState(state, action.letter)
            Action.RemoveLetter -> reduceRemoveLetterState(state)
            Action.SubmitGuess -> reduceSubmitGuessState(state)
        }
    }


    private fun reduceSubmitGuessState(state: GameState): GameState {
        if (state.isComplete || state.targetWord.length < MAX_LENGTH) {
            return state
        }

        val guessResult = MapGuessToGuessResult(state.currentInput, state.targetWord)
        val updatedGuesses = state.previousGuesses + guessResult

        return state.copy(
            previousGuesses = updatedGuesses,
            currentInput = "",
        )

    }

    private fun reduceAddLetterState(state: GameState, letter: Char): GameState {
        if (state.isComplete) {
            return state
        }

        return if (state.currentInput.length < MAX_LENGTH) {
            state.copy(
                currentInput = state.currentInput + letter,
            )
        } else {
            state
        }
    }

    private fun reduceRemoveLetterState(state: GameState): GameState {
        if (state.isComplete) {
            return state
        }

        return if (state.currentInput.isNotEmpty()) {
            state.copy(currentInput = state.currentInput.dropLast(1))
        } else {
            state
        }
    }
}

