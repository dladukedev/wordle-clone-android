package com.dladukedev.wordle.game.domain

import java.time.LocalDate

data class SummaryStatistics(
    val guess1Count: Int,
    val guess2Count: Int,
    val guess3Count: Int,
    val guess4Count: Int,
    val guess5Count: Int,
    val guess6Count: Int,

    val gamesPlayed: Int,
    val gamesWon: Int,

    val currentStreak: Int,
    val longestStreak: Int,
) {
    val winPercentage get() = (gamesWon.toFloat() / gamesPlayed.toFloat())
}
