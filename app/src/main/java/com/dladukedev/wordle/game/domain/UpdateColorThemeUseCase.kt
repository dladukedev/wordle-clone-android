package com.dladukedev.wordle.game.domain

import javax.inject.Inject
import javax.inject.Singleton

interface UpdateColorThemeUseCase {
    suspend operator fun invoke(colorThemePreference: ColorThemePreference)
}

@Singleton
class UpdateColorThemeUseCaseImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): UpdateColorThemeUseCase {
    override suspend fun invoke(colorThemePreference: ColorThemePreference) {
        preferencesRepository.updateColorTheme(colorThemePreference)
    }
}