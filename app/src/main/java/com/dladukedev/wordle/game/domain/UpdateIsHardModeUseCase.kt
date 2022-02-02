package com.dladukedev.wordle.game.domain

import javax.inject.Inject
import javax.inject.Singleton

interface UpdateIsHardModeUseCase {
    suspend operator fun invoke(isHardMode: Boolean)
}

@Singleton
class UpdateIsHardModeUseCaseImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): UpdateIsHardModeUseCase {
    override suspend fun invoke(isHardMode: Boolean) {
        preferencesRepository.updateIsHardMode(isHardMode)
    }
}