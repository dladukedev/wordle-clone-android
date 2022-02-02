package com.dladukedev.wordle.game.domain

import kotlinx.coroutines.flow.Flow

enum class ColorThemePreference {
    Dark,
    Light,
    System,
}

data class Preferences(
    val colorThemePreference: ColorThemePreference = ColorThemePreference.System,
    val isColorBlindMode: Boolean = false,
    val isHardMode: Boolean = false,
)

interface PreferencesRepository {
    suspend fun updateIsColorBlindMode(isNowColorBlindMode: Boolean)
    suspend fun updateColorTheme(themePreference: ColorThemePreference)
    suspend fun updateIsHardMode(isHardMode: Boolean)
    fun subscribeToPreferences(): Flow<Preferences>
    suspend fun getPreferences(): Preferences
}
