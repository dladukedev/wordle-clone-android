package com.dladukedev.wordle.game.state

import java.util.*

sealed class GameEvent(val id: UUID = UUID.randomUUID()) {
    object GuessSuccessfulSubmit : GameEvent()
    data class GameOverWin(val guessCount: Int) : GameEvent()
    data class GameOverLoss(val answer: String) : GameEvent()
    object GuessTooShort : GameEvent()
    object GuessInvalidWord : GameEvent()

    override fun equals(other: Any?): Boolean {
        return other is GameEvent && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}