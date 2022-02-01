package com.dladukedev.wordle.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.theme.Theme

@Composable
fun MainMenuButton(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .border(2.dp, Theme.colors.darkOnBackground)
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        content()
    }
}

@Composable
fun MainMenuButtonText(text: String, modifier: Modifier = Modifier) {
    val style = TextStyle(
        color = Theme.colors.darkOnBackground,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
    )

    MaterialTheme.typography.button

    BasicText(
        text = text.uppercase(),
        style = style,
        modifier = modifier,
    )
}