package com.dladukedev.wordle.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.dladukedev.wordle.game.domain.ColorThemePreference

@Composable
private fun ThemeProvider(
    colors: Colors,
// TODO:    typography: Typography = MaterialTheme.typography,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }.apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
    ) {
        content()
    }
}

internal val LocalColors = staticCompositionLocalOf { lightTheme }

@Composable
fun WordleTheme(themePreference: ColorThemePreference, isColorBlindMode: Boolean = false, content: @Composable () -> Unit) {
    val isNightMode = when(themePreference) {
        ColorThemePreference.Dark -> true
        ColorThemePreference.Light -> false
        ColorThemePreference.System -> isSystemInDarkTheme()
    }

    val colors = when {
        isNightMode && isColorBlindMode -> colorBlindDarkTheme
        !isNightMode && isColorBlindMode -> colorBlindLightTheme
        isNightMode && !isColorBlindMode -> darkTheme
        !isNightMode && !isColorBlindMode -> lightTheme
        else -> lightTheme
    }

    ThemeProvider(
        colors = colors,
        content = content,
    )
}

object Theme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}