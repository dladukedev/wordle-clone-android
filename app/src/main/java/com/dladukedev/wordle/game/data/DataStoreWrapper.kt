package com.dladukedev.wordle.game.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dladukedev.wordle.utils.isNotNullOrEmpty
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

data class DataStoreDailyChallengeGameState(
    val dateOffset: Int,
    val gameState: DataStoreGameState,
) {
    constructor(dateOffset: String, targetWord: String, guesses: String): this(
        dateOffset.toInt(),
        DataStoreGameState(targetWord, guesses)
    )

    val offsetToStore: String get() = dateOffset.toString()
}

data class DataStoreGameState(
    val targetWord: String,
    val guesses: List<String>,
) {
    constructor(targetWord: String, guesses: String): this(
        targetWord,
        guesses.split(",")
    )

    val guessesToStore: String get() = guesses.joinToString(",")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "save_data")

interface DataStoreWrapper {
    suspend fun getDailyChallengeSavedData(): DataStoreDailyChallengeGameState?
    suspend fun saveDailyChallengeData(dateOffset: Int, state: DataStoreGameState)
    suspend fun clearDailyChallengeSavedData()

    suspend fun getPracticeSavedData(): DataStoreGameState?
    suspend fun savePracticeData(state: DataStoreGameState)
    suspend fun clearPracticeSavedData()
}

@Singleton
class DataStoreWrapperImpl @Inject constructor(@ApplicationContext private val context: Context,) : DataStoreWrapper {
    companion object {
        private val DAILY_CHALLENGE_OFFSET_KEY = stringPreferencesKey("DAILY_CHALLENGE_OFFSET_KEY")
        private val DAILY_CHALLENGE_TARGET_KEY = stringPreferencesKey("DAILY_CHALLENGE_TARGET_KEY")
        private val DAILY_CHALLENGE_GUESSES_KEY = stringPreferencesKey("DAILY_CHALLENGE_GUESSES_KEY")

        private val PRACTICE_MODE_TARGET_KEY = stringPreferencesKey("PRACTICE_MODE_TARGET_KEY")
        private val PRACTICE_MODE_GUESSES_KEY = stringPreferencesKey("PRACTICE_MODE_GUESSES_KEY")
    }

    override suspend fun getDailyChallengeSavedData(): DataStoreDailyChallengeGameState? {
        return context.dataStore.data.map { prefs ->
            val offset = prefs[DAILY_CHALLENGE_OFFSET_KEY]
            val targetWord = prefs[DAILY_CHALLENGE_TARGET_KEY]
            val guesses = prefs[DAILY_CHALLENGE_GUESSES_KEY]

            if(offset.isNotNullOrEmpty() && targetWord.isNotNullOrEmpty() && guesses.isNotNullOrEmpty()) {
                DataStoreDailyChallengeGameState(offset, targetWord, guesses)
            } else {
                null
            }
        }.first()
    }

    override suspend fun clearDailyChallengeSavedData() {
        context.dataStore.edit { prefs ->
            prefs[DAILY_CHALLENGE_OFFSET_KEY] = ""
            prefs[DAILY_CHALLENGE_TARGET_KEY] = ""
            prefs[DAILY_CHALLENGE_GUESSES_KEY] = ""
        }
    }

    override suspend fun saveDailyChallengeData(dateOffset: Int, state: DataStoreGameState) {
        val dailyChallengeGameState = DataStoreDailyChallengeGameState(dateOffset, state)

        context.dataStore.edit { prefs ->
            prefs[DAILY_CHALLENGE_OFFSET_KEY] = dailyChallengeGameState.offsetToStore
            prefs[DAILY_CHALLENGE_TARGET_KEY] = dailyChallengeGameState.gameState.targetWord
            prefs[DAILY_CHALLENGE_GUESSES_KEY] = dailyChallengeGameState.gameState.guessesToStore
        }
    }

    override suspend fun getPracticeSavedData(): DataStoreGameState? {
        return context.dataStore.data.map { prefs ->
            val targetWord = prefs[PRACTICE_MODE_TARGET_KEY]
            val guesses = prefs[PRACTICE_MODE_GUESSES_KEY]

            if(targetWord.isNotNullOrEmpty() && guesses.isNotNullOrEmpty()) {
                DataStoreGameState(targetWord, guesses)
            } else {
                null
            }
        }.first()
    }

    override suspend fun savePracticeData(state: DataStoreGameState) {
        context.dataStore.edit { prefs ->
            prefs[PRACTICE_MODE_TARGET_KEY] = state.targetWord
            prefs[PRACTICE_MODE_GUESSES_KEY] = state.guessesToStore
        }
    }

    override suspend fun clearPracticeSavedData() {
        context.dataStore.edit { prefs ->
            prefs[PRACTICE_MODE_TARGET_KEY] = ""
            prefs[PRACTICE_MODE_GUESSES_KEY] = ""
        }
    }
}