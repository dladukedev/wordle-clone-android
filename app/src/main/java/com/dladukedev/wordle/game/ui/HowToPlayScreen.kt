package com.dladukedev.wordle.game.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.state.LetterResult
import com.dladukedev.wordle.theme.Theme

private fun LazyListScope.SmallSpacerItem() {
    item {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun LazyListScope.MediumSpacerItem() {
    item {
        Spacer(modifier = Modifier.height(16.dp))
    }
}

private fun LazyListScope.DividerItem() {
    item {
        Divider(color = Theme.colors.lightOnBackground)
    }
}

@Composable
fun HowToPlayScreen(onClose: () -> Unit) {
    val subheaderTextStyle = TextStyle(color = Theme.colors.darkOnBackground, fontWeight = FontWeight.Bold)
    val contentTextStyle = TextStyle(color = Theme.colors.darkOnBackground, fontSize = 14.sp)

    val flipAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        val duration = 400
        val easing = LinearEasing
        flipAnimation.animateTo(
            -180f,
            tween(durationMillis = duration, delayMillis = 200, easing = easing),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background)
    ) {
        Header {
            HeaderIcon(id = R.drawable.ic_close, onClick = { onClose() }, modifier = Modifier.align(Alignment.CenterStart))
            HeaderText(text = "HOW TO PLAY", modifier = Modifier.align(Alignment.Center))
        }
        LazyColumn(modifier = Modifier
            .padding(horizontal = 32.dp)
            .weight(1f)) {
            SmallSpacerItem()
            item {
                val spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = 10,
                        end = 16
                    )
                )
                val annotatedString = AnnotatedString("Guess the WORDLE in 6 tries.", spanStyles)

                BasicText(text = annotatedString, style = contentTextStyle)
            }
            SmallSpacerItem()
            item {
                BasicText(text = "Each guess must be a valid 5 letter word. Hit the enter button to submit.", style = contentTextStyle)
            }
            SmallSpacerItem()
            item {
                BasicText(text = "After each guess, the color of the tiles will change to show how close your guess was to the word.", style = contentTextStyle)
            }
            SmallSpacerItem()
            DividerItem()
            SmallSpacerItem()
            item {
                BasicText(text = "Examples", style = subheaderTextStyle)
            }
            MediumSpacerItem()
            item {
                Row(
                    modifier = Modifier.aspectRatio(6f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    FlipLetter('W', LetterResult.CORRECT, flipAnimation.value)
                    GuessLetter('E')
                    GuessLetter('A')
                    GuessLetter('R')
                    GuessLetter('Y')
                }
            }
            SmallSpacerItem()
            item {
                val spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = 11,
                        end = 12
                    )
                )
                val annotatedString = AnnotatedString("The letter W is in the word and in the correct spot.", spanStyles)

                BasicText(text = annotatedString, style = contentTextStyle)
            }
            MediumSpacerItem()
            item {
                Row(
                    modifier = Modifier.aspectRatio(6f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    GuessLetter('P')
                    FlipLetter('I', LetterResult.WRONG_LOCATION, flipAnimation.value)
                    GuessLetter('L')
                    GuessLetter('L')
                    GuessLetter('S')
                }
            }
            SmallSpacerItem()
            item {
                val spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = 11,
                        end = 12
                    )
                )
                val annotatedString = AnnotatedString("The letter I is in the word but in the wrong spot.", spanStyles)

                BasicText(text = annotatedString, style = contentTextStyle)
            }
            MediumSpacerItem()
            item {
                Row(
                    modifier = Modifier.aspectRatio(6f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {                    GuessLetter('V')
                    GuessLetter('A')
                    GuessLetter('G')
                    FlipLetter('U', LetterResult.INCORRECT, flipAnimation.value)
                    GuessLetter('E')
                }
            }
            SmallSpacerItem()
            item {
                val spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = 11,
                        end = 12
                    )
                )
                val annotatedString = AnnotatedString("The letter U is not in the word in any spot.", spanStyles)

                BasicText(text = annotatedString, style = contentTextStyle)
            }
            MediumSpacerItem()
            DividerItem()
            SmallSpacerItem()
            item {
                val spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = 6,
                        end = 21
                    ),
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = 57,
                        end = 70
                    ),
                )
                val annotatedString = AnnotatedString("A new Daily Challenge will be available each day or play Practice Mode whenever you want!", spanStyles)

                BasicText(text = annotatedString, style = contentTextStyle)
            }
        }
    }
}
