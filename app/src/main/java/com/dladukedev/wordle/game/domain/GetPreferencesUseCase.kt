package com.dladukedev.wordle.game.domain

import javax.inject.Inject
import javax.inject.Singleton

interface GetPreferencesUseCase {
    suspend operator fun invoke(): Preferences
}

@Singleton
class GetPreferencesUseCaseImpl @Inject constructor(
   private val preferencesRepository: PreferencesRepository,
): GetPreferencesUseCase {
    override suspend fun invoke(): Preferences {
        return preferencesRepository.getPreferences()
    }
}