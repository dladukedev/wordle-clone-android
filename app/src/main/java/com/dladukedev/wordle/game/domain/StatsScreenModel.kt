package com.dladukedev.wordle.game.domain

data class StatsScreenModel(
    val overviewStats: StatisticsModel,
    val dailyChallengeStats: StatisticsModel,
    val practiceModeStats: StatisticsModel,
)