package com.dladukedev.wordle.game.ui

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.state.LetterResult
import com.dladukedev.wordle.theme.Theme
import kotlinx.coroutines.launch


@Composable
fun DeleteKey(onClick: () -> Unit, modifier: Modifier = Modifier) {
    KeyboardKey(type = KeyType.Delete, onClick = onClick, modifier = modifier)
}

@Composable
fun EnterKey(onClick: () -> Unit, modifier: Modifier = Modifier) {
    KeyboardKey(type = KeyType.Enter, onClick = onClick, modifier = modifier)
}

@Composable
fun LetterKey(
    letter: Char,
    letterState: LetterResult?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val initialLetterState = remember { mutableStateOf(letterState) }

    val defaultBackgroundColor = Theme.colors.keyboardKey
    val defaultTextColor = Theme.colors.onKeyboardKey

    val backgroundColorAnimatable = remember {
        Animatable(defaultBackgroundColor)
    }
    val textColorAnimatable = remember {
        Animatable(defaultTextColor)
    }

    val background = when (letterState) {
        LetterResult.CORRECT -> Theme.colors.correctLetter
        LetterResult.WRONG_LOCATION -> Theme.colors.wrongLocationLetter
        LetterResult.INCORRECT -> Theme.colors.incorrectLetter
        else -> Theme.colors.keyboardKey
    }

    val textColor = when(letterState) {
        LetterResult.CORRECT -> Theme.colors.onCorrectLetter
        LetterResult.WRONG_LOCATION -> Theme.colors.onWrongLocationLetter
        LetterResult.INCORRECT -> Theme.colors.onIncorrectLetter
        else -> Theme.colors.onKeyboardKey
    }

    LaunchedEffect(letterState) {
        if(letterState != null) {
            val animation: AnimationSpec<Color> = if(initialLetterState.value == letterState) {
                snap()
            } else {
                tween(250, 2500)
            }
            launch {
                backgroundColorAnimatable.animateTo(background, animation)
            }
            launch {
                textColorAnimatable.animateTo(textColor, animation)
            }
        }
    }

    KeyboardKey(
        type = KeyType.Letter(letter),
        onClick = onClick,
        background = backgroundColorAnimatable.value,
        color = textColorAnimatable.value,
        modifier = modifier
    )
}

sealed class KeyType {
    object Enter: KeyType()
    object Delete: KeyType()
    data class Letter(val letter: Char): KeyType()
}

@Composable
fun KeyboardKey(
    type: KeyType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = Theme.colors.keyboardKey,
    color: Color = Theme.colors.onKeyboardKey,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .background(background)
            .padding(8.dp)
            .height(40.dp)
        ,
        contentAlignment = Alignment.Center,
    ) {
        when(type) {
            KeyType.Delete -> Icon(
                painter = painterResource(id = R.drawable.ic_backspace),
                contentDescription = null,
                tint = color
            )
            KeyType.Enter -> Text(text = "ENTER", color = color, fontSize = 10.sp)
            is KeyType.Letter -> Text(text = type.letter.toString(), color = color)
        }
    }
}

@Composable
fun Keyboard(
    letterStates: Map<Char, LetterResult>,
    onLetterSelected: (Char) -> Unit,
    onDeleteSelected: () -> Unit,
    onEnterSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf('Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P').map { letter ->
                LetterKey(
                    letter = letter,
                    letterState = letterStates[letter],
                    onClick = { onLetterSelected(letter) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(0.5f))
            listOf('A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L').map { letter ->
                LetterKey(
                    letter = letter,
                    letterState = letterStates[letter],
                    onClick = { onLetterSelected(letter) },
                    modifier = Modifier.weight(1f),
                )
            }
            Box(modifier = Modifier.weight(0.5f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EnterKey(
                onClick = onEnterSelected,
                modifier = Modifier.weight(1.5f),
            )
            listOf('Z', 'X', 'C', 'V', 'B', 'N', 'M').map { letter ->
                LetterKey(
                    letter = letter,
                    letterState = letterStates[letter],
                    onClick = { onLetterSelected(letter) },
                    modifier = Modifier.weight(1f),
                )
            }
            DeleteKey(
                onClick = onDeleteSelected,
                modifier = Modifier.weight(1.5f),
            )
        }
    }
}

@Preview
@Composable
fun KeyboardPreview() {
    Keyboard(
        letterStates = emptyMap(),
        onLetterSelected = { /* no-op */ },
        onDeleteSelected = { /* no-op */ },
        onEnterSelected = { /* no-op */ })
}