package com.dladukedev.wordle.game.data

import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface SystemThemeProvider {
    fun isSystemDarkMode(): Boolean
}

@Singleton
class SystemThemeProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): SystemThemeProvider {
    override fun isSystemDarkMode(): Boolean {
        val uiMode = context.resources.configuration.uiMode
        return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}