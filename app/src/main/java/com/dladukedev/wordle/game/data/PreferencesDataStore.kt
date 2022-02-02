package com.dladukedev.wordle.game.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

data class PreferencesDataStoreState(
    val colorThemePreference: String?,
    val isColorBlindMode: Boolean?,
    val isHardMode: Boolean?,
)

interface PreferencesDataStore {
    fun subscribeToSettings(): Flow<PreferencesDataStoreState>
    suspend fun updateIsColorBlindMode(isNowColorBlindMode: Boolean)
    suspend fun updateColorTheme(themePreference: String)
    suspend fun updateIsHardMode(isHardMode: Boolean)
}

private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

@Singleton
class PreferencesDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesDataStore {
    companion object {
        private val COLOR_THEME_PREFERENCE_KEY = stringPreferencesKey("COLOR_THEME_PREFERENCE_KEY")
        private val COLOR_BLIND_MODE_PREFERENCE_KEY =
            booleanPreferencesKey("COLOR_BLIND_MODE_PREFERENCE_KEY")
        private val HARD_MODE_PREFERENCE_KEY = booleanPreferencesKey("HARD_MODE_PREFERENCE_KEY")
    }

    override fun subscribeToSettings(): Flow<PreferencesDataStoreState> {
        return context.preferencesDataStore.data.map { prefs ->
            PreferencesDataStoreState(
                prefs[COLOR_THEME_PREFERENCE_KEY],
                prefs[COLOR_BLIND_MODE_PREFERENCE_KEY],
                prefs[HARD_MODE_PREFERENCE_KEY],
            )
        }
    }

    override suspend fun updateIsColorBlindMode(isNowColorBlindMode: Boolean) {
        context.preferencesDataStore.edit { prefs ->
            prefs[COLOR_BLIND_MODE_PREFERENCE_KEY] = isNowColorBlindMode
        }
    }

    override suspend fun updateColorTheme(themePreference: String) {
        context.preferencesDataStore.edit { prefs ->
            prefs[COLOR_THEME_PREFERENCE_KEY] = themePreference
        }
    }

    override suspend fun updateIsHardMode(isHardMode: Boolean) {
        context.preferencesDataStore.edit { prefs ->
            prefs[HARD_MODE_PREFERENCE_KEY] = isHardMode
        }
    }
}