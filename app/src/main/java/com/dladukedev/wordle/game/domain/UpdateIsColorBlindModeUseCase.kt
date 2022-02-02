package com.dladukedev.wordle.game.domain

import javax.inject.Inject
import javax.inject.Singleton

interface UpdateIsColorBlindModeUseCase {
    suspend operator fun invoke(isColorBlindMode: Boolean)
}

@Singleton
class UpdateIsColorBlindModeUseCaseImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): UpdateIsColorBlindModeUseCase {
    override suspend fun invoke(isColorBlindMode: Boolean) {
        preferencesRepository.updateIsColorBlindMode(isColorBlindMode)
    }
}