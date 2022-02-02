package com.dladukedev.wordle.game.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface SubscribeToPreferencesUseCase {
    operator fun invoke(): Flow<Preferences>
}

@Singleton
class SubscribeToPreferencesUseCaseImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): SubscribeToPreferencesUseCase {
    override fun invoke(): Flow<Preferences> {
        return preferencesRepository.subscribeToPreferences()
    }

}