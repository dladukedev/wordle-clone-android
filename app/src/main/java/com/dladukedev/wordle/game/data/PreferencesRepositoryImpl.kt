package com.dladukedev.wordle.game.data

import com.dladukedev.wordle.game.domain.ColorThemePreference
import com.dladukedev.wordle.game.domain.Preferences
import com.dladukedev.wordle.game.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
) : PreferencesRepository {

    override suspend fun updateIsColorBlindMode(isNowColorBlindMode: Boolean) {
        return preferencesDataStore.updateIsColorBlindMode(isNowColorBlindMode)
    }

    override suspend fun updateColorTheme(themePreference: ColorThemePreference) {
        return preferencesDataStore.updateColorTheme(themePreference.name)
    }

    override suspend fun updateIsHardMode(isHardMode: Boolean) {
        return preferencesDataStore.updateIsHardMode(isHardMode)
    }

    override fun subscribeToPreferences(): Flow<Preferences> {
        return preferencesDataStore.subscribeToSettings().map { preferences ->
            Preferences(
                preferences.colorThemePreference?.let { ColorThemePreference.valueOf(it) } ?: ColorThemePreference.System,
                preferences.isColorBlindMode ?: false,
                preferences.isHardMode ?: false,
            )
        }
    }

    override suspend fun getPreferences(): Preferences {
        return subscribeToPreferences().first()
    }

}