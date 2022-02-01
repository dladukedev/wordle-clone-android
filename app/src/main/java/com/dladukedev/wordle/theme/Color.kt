package com.dladukedev.wordle.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class Colors(
    background: Color,
    darkOnBackground: Color,
    mediumOnBackground: Color,
    lightOnBackground: Color,
    correctLetter: Color,
    onCorrectLetter: Color,
    wrongLocationLetter: Color,
    onWrongLocationLetter: Color,
    incorrectLetter: Color,
    onIncorrectLetter: Color,
    keyboardKey: Color,
    onKeyboardKey: Color,
    snackbar: Color,
    onSnackbar: Color,
) {
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var darkOnBackground by mutableStateOf(darkOnBackground, structuralEqualityPolicy())
        internal set
    var mediumOnBackground by mutableStateOf(mediumOnBackground, structuralEqualityPolicy())
        internal set
    var lightOnBackground by mutableStateOf(lightOnBackground, structuralEqualityPolicy())
        internal set
    var correctLetter by mutableStateOf(correctLetter, structuralEqualityPolicy())
        internal set
    var onCorrectLetter by mutableStateOf(onCorrectLetter, structuralEqualityPolicy())
        internal set
    var wrongLocationLetter by mutableStateOf(wrongLocationLetter, structuralEqualityPolicy())
        internal set
    var onWrongLocationLetter by mutableStateOf(onWrongLocationLetter, structuralEqualityPolicy())
        internal set
    var incorrectLetter by mutableStateOf(incorrectLetter, structuralEqualityPolicy())
        internal set
    var onIncorrectLetter by mutableStateOf(onIncorrectLetter, structuralEqualityPolicy())
        internal set
    var keyboardKey by mutableStateOf(keyboardKey, structuralEqualityPolicy())
        internal set
    var onKeyboardKey by mutableStateOf(onKeyboardKey, structuralEqualityPolicy())
        internal set
    var toast by mutableStateOf(snackbar, structuralEqualityPolicy())
        internal set
    var onToast by mutableStateOf(onSnackbar, structuralEqualityPolicy())
        internal set

    fun updateColorsFrom(other: Colors) {
        background = other.background
        darkOnBackground = other.darkOnBackground
        mediumOnBackground = other.mediumOnBackground
        lightOnBackground = other.lightOnBackground
        correctLetter = other.correctLetter
        onCorrectLetter = other.onCorrectLetter
        wrongLocationLetter = other.wrongLocationLetter
        onWrongLocationLetter = other.onWrongLocationLetter
        incorrectLetter = other.incorrectLetter
        onIncorrectLetter = other.onIncorrectLetter
        keyboardKey = other.keyboardKey
        onKeyboardKey = other.onKeyboardKey
        toast = other.toast
        onToast = other.onToast
    }

    fun copy(
        background: Color = this.background,
        darkOnBackground: Color = this.darkOnBackground,
        mediumOnBackground: Color = this.mediumOnBackground,
        lightOnBackground: Color = this.lightOnBackground,
        correctLetter: Color = this.correctLetter,
        onCorrectLetter: Color = this.onCorrectLetter,
        wrongLocationLetter: Color = this.wrongLocationLetter,
        onWrongLocationLetter: Color = this.onWrongLocationLetter,
        incorrectLetter: Color = this.incorrectLetter,
        onIncorrectLetter: Color = this.onIncorrectLetter,
        keyboardKey: Color = this.keyboardKey,
        onKeyboardKey: Color = this.onKeyboardKey,
        snackbar: Color = this.toast,
        onSnackbar: Color = this.onToast,
    ): Colors {
        return Colors(
            background,
            darkOnBackground,
            mediumOnBackground,
            lightOnBackground,
            correctLetter,
            onCorrectLetter,
            wrongLocationLetter,
            onWrongLocationLetter,
            incorrectLetter,
            onIncorrectLetter,
            keyboardKey,
            onKeyboardKey,
            snackbar,
            onSnackbar,
        )
    }
}

val darkTheme = Colors(
    Color(0xFF121213),
    Color(0xFFD7DADC),
    Color(0xFF565758),
    Color(0xFF3A3A3C),

    Color(0xFF538D4E),
    Color(0xFFD7DADC),
    Color(0xFFB59F3B),
    Color(0xFFD7DADC),
    Color(0xFF3A3A3C),
    Color(0xFFD7DADC),

    Color(0xFF818384),
    Color(0xFFD7DADC),

    Color(0xFFD7DADC),
    Color(0xFF121213),
)

val lightTheme = Colors(
    Color(0xFFFFFFFF),
    Color(0xFF1A1A1B),
    Color(0xFF878A8C),
    Color(0xFFD3D6DA),

    Color(0xFF6AAA64),
    Color(0xFFFFFFFF),
    Color(0xFFC9B458),
    Color(0xFFFFFFFF),
    Color(0xFF787C7E),
    Color(0xFFFFFFFF),

    Color(0xFFD3D6DA),
    Color(0xFF1A1A1B),

    Color(0xFFD7DADC),
    Color(0xFF121213),
)

val colorBlindDarkTheme = Colors(
    Color(0xFF121213),
    Color(0xFFD7DADC),
    Color(0xFF565758),
    Color(0xFF3A3A3C),

    Color(0xFFF5793A),
    Color(0xFFD7DADC),
    Color(0xFF85C0F9),
    Color(0xFFD7DADC),
    Color(0xFF3A3A3C),
    Color(0xFFD7DADC),

    Color(0xFF818384),
    Color(0xFFD7DADC),

    Color(0xFFD7DADC),
    Color(0xFF121213),
)

val colorBlindLightTheme = Colors(
    Color(0xFFFFFFFF),
    Color(0xFF1A1A1B),
    Color(0xFF878A8C),
    Color(0xFFD3D6DA),

    Color(0xFFF5793A),
    Color(0xFFFFFFFF),
    Color(0xFF85C0F9),
    Color(0xFFFFFFFF),
    Color(0xFF787C7E),
    Color(0xFFFFFFFF),

    Color(0xFFD3D6DA),
    Color(0xFF1A1A1B),

    Color(0xFFD7DADC),
    Color(0xFF121213),
)


