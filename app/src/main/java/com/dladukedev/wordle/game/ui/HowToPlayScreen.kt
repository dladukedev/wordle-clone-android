package com.dladukedev.wordle.game.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.state.LetterResult
import com.dladukedev.wordle.theme.Theme

@Composable
private fun SmallSpacerItem() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun MediumSpacerItem() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun DividerItem() {
    Divider(color = Theme.colors.lightOnBackground)
}

private object HowToPlayStyledDisplayText {
    val guesses = buildAnnotatedString {
        append("Guess the ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("WORDLE")
        pop()
        append(" in 6 tries.")
    }
    val correctGuess = buildAnnotatedString {
        append("The letter ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("W")
        pop()
        append(" is in the word and in the correct spot.")
    }
    val incorrectLocationGuess = buildAnnotatedString {
        append("The letter ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("I")
        pop()
        append(" is in the word but in the wrong spot.")
    }
    val incorrectGuess = buildAnnotatedString {
        append("The letter ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("U")
        pop()
        append(" is not in the word in any spot.")
    }
    val modes = buildAnnotatedString {
        append("A new ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("Daily Challenge")
        pop()
        append(" will be available each day or play ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("Practice Mode")
        pop()
        append(" whenever you want!")
    }
}

@Composable
fun HowToPlayScreen(onClose: () -> Unit) {
    val subheaderTextStyle =
        TextStyle(color = Theme.colors.darkOnBackground, fontWeight = FontWeight.Bold)
    val contentTextStyle = TextStyle(color = Theme.colors.darkOnBackground, fontSize = 14.sp)

    val flipAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        val duration = 400
        val easing = LinearEasing
        flipAnimation.animateTo(
            -180f,
            tween(durationMillis = duration, delayMillis = 400, easing = easing),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background)
    ) {
        Header {
            HeaderIcon(
                id = R.drawable.ic_close,
                onClick = { onClose() },
                modifier = Modifier.align(Alignment.CenterStart)
            )
            HeaderText(text = "HOW TO PLAY", modifier = Modifier.align(Alignment.Center))
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SmallSpacerItem()

            BasicText(text = HowToPlayStyledDisplayText.guesses, style = contentTextStyle)
            SmallSpacerItem()
            BasicText(
                text = "Each guess must be a valid 5 letter word. Hit the enter button to submit.",
                style = contentTextStyle
            )
            SmallSpacerItem()
            BasicText(
                text = "After each guess, the color of the tiles will change to show how close your guess was to the word.",
                style = contentTextStyle
            )
            SmallSpacerItem()
            DividerItem()
            SmallSpacerItem()
            BasicText(text = "Examples", style = subheaderTextStyle)
            MediumSpacerItem()
            Row(
                modifier = Modifier
                    .widthIn(max = 512.dp)
                    .aspectRatio(6f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                FlipLetter('W', LetterResult.CORRECT, flipAnimation.value)
                GuessLetter('E')
                GuessLetter('A')
                GuessLetter('R')
                GuessLetter('Y')
            }
            SmallSpacerItem()
            BasicText(text = HowToPlayStyledDisplayText.correctGuess, style = contentTextStyle)
            MediumSpacerItem()
            Row(
                modifier = Modifier
                    .widthIn(max = 512.dp)
                    .aspectRatio(6f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                GuessLetter('P')
                FlipLetter('I', LetterResult.WRONG_LOCATION, flipAnimation.value)
                GuessLetter('L')
                GuessLetter('L')
                GuessLetter('S')
            }
            SmallSpacerItem()
            BasicText(
                text = HowToPlayStyledDisplayText.incorrectLocationGuess,
                style = contentTextStyle
            )
            MediumSpacerItem()
            Row(
                modifier = Modifier
                    .widthIn(max = 512.dp)
                    .aspectRatio(6f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                GuessLetter('V')
                GuessLetter('A')
                GuessLetter('G')
                FlipLetter('U', LetterResult.INCORRECT, flipAnimation.value)
                GuessLetter('E')
            }
            SmallSpacerItem()
            BasicText(text = HowToPlayStyledDisplayText.incorrectGuess, style = contentTextStyle)
            MediumSpacerItem()
            DividerItem()
            SmallSpacerItem()
            BasicText(text = HowToPlayStyledDisplayText.modes, style = contentTextStyle)
        }
    }
}
