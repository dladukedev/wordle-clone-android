package com.dladukedev.wordle.game.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameResult(
    val isWin: Boolean,
    val targetWord: String,
    val streak: Int,
    val dateOffset: Int?,

    val guessOne: String?,
    val guessTwo: String?,
    val guessThree: String?,
    val guessFour: String?,
    val guessFive: String?,
    val guessSix: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}