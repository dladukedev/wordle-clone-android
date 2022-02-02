package com.dladukedev.wordle.game.preferences

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dladukedev.wordle.game.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPreferencesViewModel @Inject constructor(
    subscribeToPreferences: SubscribeToPreferencesUseCase,
    private val updateIsHardMode: UpdateIsHardModeUseCase,
    private val updateIsColorBlindMode: UpdateIsColorBlindModeUseCase,
    private val updateColorTheme: UpdateColorThemeUseCase
): ViewModel() {
    val currentPreferences = subscribeToPreferences()

    fun updateIsColorBlindPreference(isColorBlindMode: Boolean) {
        viewModelScope.launch { updateIsColorBlindMode(isColorBlindMode) }
    }

    fun updateIsHardModePreference(isHardMode: Boolean) {
        viewModelScope.launch { updateIsHardMode(isHardMode) }
    }

    fun updateColorThemePreference(colorThemePreference: ColorThemePreference) {
        Log.i("TAG", "BEFORE")
        viewModelScope.launch { updateColorTheme(colorThemePreference) }
        Log.i("TAG", "AFTER")
    }
}