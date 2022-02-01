package com.dladukedev.wordle.game.state

import java.util.*

sealed class GameEvent(val id: UUID = UUID.randomUUID()) {
    object GuessSuccessfulSubmit: GameEvent()
    data class GameOverWin(val guessCount: Int) : GameEvent()
    data class GameOverLoss(val answer: String) : GameEvent()
    object GuessTooShort : GameEvent()
    object GuessInvalidWord : GameEvent()
}