package com.dladukedev.wordle

import androidx.lifecycle.ViewModel
import com.dladukedev.wordle.game.domain.GetPreferencesUseCase
import com.dladukedev.wordle.game.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val getPreferences: GetPreferencesUseCase
): ViewModel() {
    fun getInitialPreferences(): Preferences = runBlocking { getPreferences() }
}