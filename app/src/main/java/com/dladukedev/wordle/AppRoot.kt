package com.dladukedev.wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dladukedev.wordle.game.ui.ToastContainer
import com.dladukedev.wordle.game.ui.ToastStore
import com.dladukedev.wordle.theme.Theme
import com.dladukedev.wordle.theme.WordleTheme

val LocalToastStore = compositionLocalOf<ToastStore> { error("LocalToastStore Not Initialized") }

@Composable
fun AppRoot() {
    WordleTheme {
        CompositionLocalProvider(LocalToastStore provides ToastStore()) {
            // A surface container using the 'background' color from the theme
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background),
            ) {
                Router(modifier = Modifier.fillMaxSize())
                ToastContainer(modifier = Modifier.align(Alignment.TopCenter).offset(y = 88.dp))
            }
        }
    }
}