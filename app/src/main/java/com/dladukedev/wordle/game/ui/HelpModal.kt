package com.dladukedev.wordle.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.dladukedev.wordle.theme.Theme

@Composable
fun HelpModal(onDismissRequested: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequested) {
        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {

        }
    }
}