package com.dladukedev.wordle.game.domain

import com.dladukedev.wordle.game.data.SystemThemeProvider
import javax.inject.Inject
import javax.inject.Singleton

enum class AppTheme {
    Dark, Light
}

interface GetCurrentAppThemeUseCase {
    suspend operator fun invoke(): AppTheme
}

@Singleton
class GetCurrentAppThemeUseCaseImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val systemThemeProvider: SystemThemeProvider,
): GetCurrentAppThemeUseCase {
    override suspend fun invoke(): AppTheme {
        return when(preferencesRepository.getPreferences().colorThemePreference) {
            ColorThemePreference.Dark -> AppTheme.Dark
            ColorThemePreference.Light -> AppTheme.Light
            ColorThemePreference.System -> {
                if(systemThemeProvider.isSystemDarkMode()) {
                    AppTheme.Dark
                } else {
                    AppTheme.Light
                }
            }
        }
    }

}