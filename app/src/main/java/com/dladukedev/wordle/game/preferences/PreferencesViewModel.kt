package com.dladukedev.wordle.game.preferences

import androidx.lifecycle.ViewModel
import com.dladukedev.wordle.game.domain.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
): ViewModel() {
    val preferences = preferencesRepository.subscribeToPreferences()
}