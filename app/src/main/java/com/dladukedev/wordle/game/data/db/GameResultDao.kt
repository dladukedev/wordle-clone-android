package com.dladukedev.wordle.game.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameResultDao {
    @Query("SELECT streak FROM gameResult WHERE dateOffset IS NULL ORDER BY id DESC")
    suspend fun getPracticeModeCurrentStreak(): Int?

    @Query("SELECT max(streak) FROM gameResult WHERE dateOffset IS NULL ")
    suspend fun getPracticeModeLongestStreak(): Int?

    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NULL AND guessOne IS NOT NULL AND guessTwo IS NULL")
    suspend fun getPracticeModeGuessOneCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NULL AND guessTwo IS NOT NULL AND guessThree IS NULL")
    suspend fun getPracticeModeGuessTwoCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NULL AND guessThree IS NOT NULL AND guessFour IS NULL")
    suspend fun getPracticeModeGuessThreeCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NULL AND guessFour IS NOT NULL AND guessFive IS NULL")
    suspend fun getPracticeModeGuessFourCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NULL AND guessFive IS NOT NULL AND guessSix IS NULL")
    suspend fun getPracticeModeGuessFiveCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NULL AND guessSix IS NOT NULL AND isWin = 1")
    suspend fun getPracticeModeGuessSixCount(): Int

    @Query("SELECT count(id) FROM gameResult WHERE dateOffset IS NULL")
    suspend fun getPracticeModeGameCount(): Int

    @Query("SELECT count(id) FROM gameResult WHERE dateOffset IS NULL AND isWin = 1")
    suspend fun getPracticeModeGameWinCount(): Int

    @Query("SELECT guessOne, guessTwo, guessThree, guessFour, guessFive, guessSix FROM gameResult WHERE dateOffset IS NULL")
    suspend fun getPracticeModeGuessWords(): List<GuessWords>

    @Query("SELECT streak FROM gameResult WHERE dateOffset = :checkDateOffset ORDER BY id DESC")
    suspend fun getDailyChallengeCurrentStreak(checkDateOffset: Int): Int?

    @Query("SELECT max(streak) FROM gameResult WHERE dateOffset IS NOT NULL")
    suspend fun getDailyChallengeLongestStreak(): Int?

    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NOT NULL AND guessOne IS NOT NULL AND guessTwo IS NULL")
    suspend fun getDailyChallengeGuessOneCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NOT NULL AND guessTwo IS NOT NULL AND guessThree IS NULL")
    suspend fun getDailyChallengeGuessTwoCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NOT NULL AND guessThree IS NOT NULL AND guessFour IS NULL")
    suspend fun getDailyChallengeGuessThreeCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NOT NULL AND guessFour IS NOT NULL AND guessFive IS NULL")
    suspend fun getDailyChallengeGuessFourCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NOT NULL AND guessFive IS NOT NULL AND guessSix IS NULL")
    suspend fun getDailyChallengeGuessFiveCount(): Int
    @Query("SELECT count(*) FROM gameResult WHERE dateOffset IS NOT NULL AND guessSix IS NOT NULL AND isWin = 1")
    suspend fun getDailyChallengeGuessSixCount(): Int

    @Query("SELECT count(id) FROM gameResult WHERE dateOffset IS NOT NULL")
    suspend fun getDailyChallengeGameCount(): Int

    @Query("SELECT count(id) FROM gameResult WHERE dateOffset IS NOT NULL AND isWin = 1")
    suspend fun getDailyChallengeGameWinCount(): Int

    @Query("SELECT guessOne, guessTwo, guessThree, guessFour, guessFive, guessSix FROM gameResult WHERE dateOffset IS NOT NULL")
    suspend fun getDailyChallengeGuessWords(): List<GuessWords>

    @Insert
    suspend fun saveGameResult(gameResult: GameResult)
}