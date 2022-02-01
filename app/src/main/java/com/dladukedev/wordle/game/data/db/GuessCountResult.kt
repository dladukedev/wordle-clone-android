package com.dladukedev.wordle.game.data.db

import androidx.room.ColumnInfo

data class GuessCountResult(
    @ColumnInfo(name = "guess_one")
    val guessOneCount: Int,

    @ColumnInfo(name = "guess_two")
    val guessTwoCount: Int,

    @ColumnInfo(name = "guess_three")
    val guessThreeCount: Int,

    @ColumnInfo(name = "guess_four")
    val guessFourCount: Int,

    @ColumnInfo(name = "guess_five")
    val guessFiveCount: Int,

    @ColumnInfo(name = "guess_six")
    val guessSixCount: Int,
)