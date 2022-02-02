package com.dladukedev.wordle.game.data

import com.dladukedev.wordle.game.data.db.GameResult
import com.dladukedev.wordle.game.data.db.GameResultDao
import com.dladukedev.wordle.game.domain.GameState
import com.dladukedev.wordle.game.domain.StatisticsModel
import com.dladukedev.wordle.game.domain.StatisticsRepository
import com.dladukedev.wordle.game.domain.SummaryStatistics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepositoryImpl @Inject constructor(
    private val gameResultDao: GameResultDao,
) : StatisticsRepository {
    private fun mapGameStateToGameResult(
        state: GameState,
        newStreak: Int,
        offset: Int? = null
    ): GameResult {
        return GameResult(
            isWin = state.isWin,
            targetWord = state.targetWord,
            streak = newStreak,
            dateOffset = offset,
            guessOne = state.previousGuesses.getOrNull(0)?.toString(),
            guessTwo = state.previousGuesses.getOrNull(1)?.toString(),
            guessThree = state.previousGuesses.getOrNull(2)?.toString(),
            guessFour = state.previousGuesses.getOrNull(3)?.toString(),
            guessFive = state.previousGuesses.getOrNull(4)?.toString(),
            guessSix = state.previousGuesses.getOrNull(5)?.toString(),
        )
    }

    override suspend fun saveDailyChallengeResult(offset: Int, state: GameState) {
        val newStreak = if (state.isWin) {
            val previousDateOffset = offset - 1
            val currentStreak = gameResultDao.getDailyChallengeCurrentStreak(previousDateOffset) ?: 0
            currentStreak + 1
        } else {
            0
        }

        val result = mapGameStateToGameResult(state, newStreak)

        val dailyResult = result.copy(dateOffset = offset)

        gameResultDao.saveGameResult(dailyResult)
    }

    override suspend fun savePracticeModeResult(state: GameState) {
        val newStreak = if (state.isWin) {
            val currentStreak = gameResultDao.getPracticeModeCurrentStreak() ?: 0
            currentStreak + 1
        } else {
            0
        }

        val result = mapGameStateToGameResult(state, newStreak)

        gameResultDao.saveGameResult(result)
    }

    override suspend fun loadDailyChallengeStatSummary(offset: Int): SummaryStatistics {
        val guess1Count = gameResultDao.getDailyChallengeGuessOneCount()
        val guess2Count = gameResultDao.getDailyChallengeGuessTwoCount()
        val guess3Count = gameResultDao.getDailyChallengeGuessThreeCount()
        val guess4Count = gameResultDao.getDailyChallengeGuessFourCount()
        val guess5Count = gameResultDao.getDailyChallengeGuessFiveCount()
        val guess6Count = gameResultDao.getDailyChallengeGuessSixCount()

        val gamesPlayed = gameResultDao.getDailyChallengeGameCount()
        val gamesWon = gameResultDao.getDailyChallengeGameWinCount()

        val currentStreak = gameResultDao.getDailyChallengeCurrentStreak(offset)
        val longestStreak = gameResultDao.getDailyChallengeLongestStreak()

        return SummaryStatistics(
            guess1Count = guess1Count,
            guess2Count = guess2Count,
            guess3Count = guess3Count,
            guess4Count = guess4Count,
            guess5Count = guess5Count,
            guess6Count = guess6Count,

            gamesPlayed = gamesPlayed,
            gamesWon = gamesWon,

            currentStreak = currentStreak ?: 0,
            longestStreak = longestStreak ?: 0,
        )
    }

    override suspend fun loadPracticeModeStatSummary(): SummaryStatistics {
        val guess1Count = gameResultDao.getPracticeModeGuessOneCount()
        val guess2Count = gameResultDao.getPracticeModeGuessTwoCount()
        val guess3Count = gameResultDao.getPracticeModeGuessThreeCount()
        val guess4Count = gameResultDao.getPracticeModeGuessFourCount()
        val guess5Count = gameResultDao.getPracticeModeGuessFiveCount()
        val guess6Count = gameResultDao.getPracticeModeGuessSixCount()

        val gamesPlayed = gameResultDao.getPracticeModeGameCount()
        val gamesWon = gameResultDao.getPracticeModeGameWinCount()

        val currentStreak = gameResultDao.getPracticeModeCurrentStreak()
        val longestStreak = gameResultDao.getPracticeModeLongestStreak()

        return SummaryStatistics(
            guess1Count = guess1Count,
            guess2Count = guess2Count,
            guess3Count = guess3Count,
            guess4Count = guess4Count,
            guess5Count = guess5Count,
            guess6Count = guess6Count,

            gamesPlayed = gamesPlayed,
            gamesWon = gamesWon,

            currentStreak = currentStreak ?: 0,
            longestStreak = longestStreak ?: 0,
        )
    }

    override suspend fun loadPracticeModeStats(): StatisticsModel {
        val guess1Count = gameResultDao.getPracticeModeGuessOneCount()
        val guess2Count = gameResultDao.getPracticeModeGuessTwoCount()
        val guess3Count = gameResultDao.getPracticeModeGuessThreeCount()
        val guess4Count = gameResultDao.getPracticeModeGuessFourCount()
        val guess5Count = gameResultDao.getPracticeModeGuessFiveCount()
        val guess6Count = gameResultDao.getPracticeModeGuessSixCount()

        val gamesPlayed = gameResultDao.getPracticeModeGameCount()
        val gamesWon = gameResultDao.getPracticeModeGameWinCount()

        val currentStreak = gameResultDao.getPracticeModeCurrentStreak()
        val longestStreak = gameResultDao.getPracticeModeLongestStreak()

        val words = gameResultDao.getPracticeModeGuessWords()
            .flatMap { listOfNotNull(it.guessOne, it.guessTwo, it.guessThree, it.guessFour, it.guessFive, it.guessSix) }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(5)

        return StatisticsModel(
            guess1Count = guess1Count,
            guess2Count = guess2Count,
            guess3Count = guess3Count,
            guess4Count = guess4Count,
            guess5Count = guess5Count,
            guess6Count = guess6Count,

            gamesPlayed = gamesPlayed,
            gamesWon = gamesWon,

            currentStreak = currentStreak ?: 0,
            longestStreak = longestStreak ?: 0,

            topWords = words
        )
    }

    override suspend fun loadDailyChallengeStats(offset: Int): StatisticsModel {
        val guess1Count = gameResultDao.getDailyChallengeGuessOneCount()
        val guess2Count = gameResultDao.getDailyChallengeGuessTwoCount()
        val guess3Count = gameResultDao.getDailyChallengeGuessThreeCount()
        val guess4Count = gameResultDao.getDailyChallengeGuessFourCount()
        val guess5Count = gameResultDao.getDailyChallengeGuessFiveCount()
        val guess6Count = gameResultDao.getDailyChallengeGuessSixCount()

        val gamesPlayed = gameResultDao.getDailyChallengeGameCount()
        val gamesWon = gameResultDao.getDailyChallengeGameWinCount()

        // If today's puzzle isn't done, grab the standing streak from yesterday
        val currentStreak = gameResultDao.getDailyChallengeCurrentStreak(offset) ?: gameResultDao.getDailyChallengeCurrentStreak(offset-1)

        val longestStreak = gameResultDao.getDailyChallengeLongestStreak()

        val words = gameResultDao.getDailyChallengeGuessWords()
            .flatMap { listOfNotNull(it.guessOne, it.guessTwo, it.guessThree, it.guessFour, it.guessFive, it.guessSix) }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(5)

        return StatisticsModel(
            guess1Count = guess1Count,
            guess2Count = guess2Count,
            guess3Count = guess3Count,
            guess4Count = guess4Count,
            guess5Count = guess5Count,
            guess6Count = guess6Count,

            gamesPlayed = gamesPlayed,
            gamesWon = gamesWon,

            currentStreak = currentStreak ?: 0,
            longestStreak = longestStreak ?: 0,

            topWords = words
        )
    }

    override suspend fun loadTotalStats(): StatisticsModel {
        val guess1Count = gameResultDao.getPracticeModeGuessOneCount() + gameResultDao.getDailyChallengeGuessOneCount()
        val guess2Count = gameResultDao.getPracticeModeGuessTwoCount() + gameResultDao.getDailyChallengeGuessTwoCount()
        val guess3Count = gameResultDao.getPracticeModeGuessThreeCount() + gameResultDao.getDailyChallengeGuessThreeCount()
        val guess4Count = gameResultDao.getPracticeModeGuessFourCount() + gameResultDao.getDailyChallengeGuessFourCount()
        val guess5Count = gameResultDao.getPracticeModeGuessFiveCount() + gameResultDao.getDailyChallengeGuessFiveCount()
        val guess6Count = gameResultDao.getPracticeModeGuessSixCount() + gameResultDao.getDailyChallengeGuessSixCount()

        val gamesPlayed = gameResultDao.getPracticeModeGameCount() + gameResultDao.getDailyChallengeGameCount()
        val gamesWon = gameResultDao.getPracticeModeGameWinCount() + gameResultDao.getDailyChallengeGameWinCount()

        val words = (gameResultDao.getDailyChallengeGuessWords() + gameResultDao.getPracticeModeGuessWords())
            .flatMap { listOfNotNull(it.guessOne, it.guessTwo, it.guessThree, it.guessFour, it.guessFive, it.guessSix) }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(5)

        return StatisticsModel(
            guess1Count = guess1Count,
            guess2Count = guess2Count,
            guess3Count = guess3Count,
            guess4Count = guess4Count,
            guess5Count = guess5Count,
            guess6Count = guess6Count,

            gamesPlayed = gamesPlayed,
            gamesWon = gamesWon,

            currentStreak = null,
            longestStreak = null,

            topWords = words
        )

    }
}