package com.dladukedev.wordle.game.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.game.domain.GuessResult
import com.dladukedev.wordle.game.state.LetterResult
import com.dladukedev.wordle.theme.Theme

@Composable
fun FlipLetter(letter: Char, result: LetterResult, rotation: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .graphicsLayer {
                rotationX = rotation
            },
    ) {
        if (rotation <= -90) {
            PreviousGuessLetter(
                letter = letter, result = result,
                modifier = Modifier.graphicsLayer {
                    rotationX = -180f
                },
            )
        } else {
            GuessLetter(letter = letter)
        }
    }
}

@Composable
fun PreviousGuessLetter(letter: Char, result: LetterResult, modifier: Modifier = Modifier) {
    val background = when (result) {
        LetterResult.CORRECT -> Theme.colors.correctLetter
        LetterResult.WRONG_LOCATION -> Theme.colors.wrongLocationLetter
        LetterResult.INCORRECT -> Theme.colors.incorrectLetter
    }

    val textColor = when (result) {
        LetterResult.CORRECT -> Theme.colors.onCorrectLetter
        LetterResult.WRONG_LOCATION -> Theme.colors.onWrongLocationLetter
        LetterResult.INCORRECT -> Theme.colors.onIncorrectLetter
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(1.dp, background)
            .background(background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter.toString(),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun PreviousGuess(guess: GuessResult, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PreviousGuessLetter(
            letter = guess.firstLetter,
            result = guess.firstLetterResult,
            modifier = modifier.weight(1f)
        )
        PreviousGuessLetter(
            letter = guess.secondLetter,
            result = guess.secondLetterResult,
            modifier = modifier.weight(1f)
        )
        PreviousGuessLetter(
            letter = guess.thirdLetter,
            result = guess.thirdLetterResult,
            modifier = modifier.weight(1f)
        )
        PreviousGuessLetter(
            letter = guess.fourthLetter,
            result = guess.fourthLetterResult,
            modifier = modifier.weight(1f)
        )
        PreviousGuessLetter(
            letter = guess.fifthLetter,
            result = guess.fifthLetterResult,
            modifier = modifier.weight(1f)
        )
    }
}

@Composable
fun GuessLetter(letter: Char?, modifier: Modifier = Modifier) {
    val borderColor = if (letter == null) {
        Theme.colors.lightOnBackground
    } else {
        Theme.colors.mediumOnBackground
    }

    Box(
        modifier = modifier
            .border(1.dp, borderColor)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter?.toString() ?: "",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Theme.colors.darkOnBackground,
        )
    }
}

@Composable
fun CurrentGuess(guess: List<Char>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GuessLetter(letter = guess.getOrNull(0), modifier = modifier.weight(1f))
        GuessLetter(letter = guess.getOrNull(1), modifier = modifier.weight(1f))
        GuessLetter(letter = guess.getOrNull(2), modifier = modifier.weight(1f))
        GuessLetter(letter = guess.getOrNull(3), modifier = modifier.weight(1f))
        GuessLetter(letter = guess.getOrNull(4), modifier = modifier.weight(1f))
    }
}

@Composable
fun FutureGuess(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GuessLetter(letter = null, modifier = modifier.weight(1f))
        GuessLetter(letter = null, modifier = modifier.weight(1f))
        GuessLetter(letter = null, modifier = modifier.weight(1f))
        GuessLetter(letter = null, modifier = modifier.weight(1f))
        GuessLetter(letter = null, modifier = modifier.weight(1f))
    }
}

@Composable
fun GameBoard(
    currentGuess: List<Char>,
    previousGuesses: List<GuessResult>,
    modifier: Modifier = Modifier,
    shakeCurrentRow: Boolean = false,
    onShakeComplete: () -> Unit = {},
    flipRow: Boolean = false,
    onFlipRowComplete: () -> Unit = {},
) {
    val futureGuessesRemaining = 5 - previousGuesses.size

    val shakeAnimation = remember {
        Animatable(0f)
    }

    val card1Animation = remember {
        Animatable(0f)
    }
    val card2Animation = remember {
        Animatable(0f)
    }
    val card3Animation = remember {
        Animatable(0f)
    }
    val card4Animation = remember {
        Animatable(0f)
    }
    val card5Animation = remember {
        Animatable(0f)
    }

    LaunchedEffect(shakeCurrentRow, flipRow) {
        if (shakeCurrentRow) {
            val easing = LinearEasing
            shakeAnimation.animateTo(0f, tween(15, easing = easing))
            shakeAnimation.animateTo(1f, tween(15, easing = easing))
            shakeAnimation.animateTo(0f, tween(15, easing = easing))
            shakeAnimation.animateTo(-1f, tween(15, easing = easing))
            shakeAnimation.animateTo(0f, tween(15, easing = easing))
            shakeAnimation.animateTo(1f, tween(15, easing = easing))
            shakeAnimation.animateTo(0f, tween(15, easing = easing))
            shakeAnimation.animateTo(-1f, tween(15, easing = easing))
            shakeAnimation.animateTo(0f, tween(15, easing = easing))
            shakeAnimation.animateTo(1f, tween(15, easing = easing))
            shakeAnimation.animateTo(0f, tween(15, easing = easing))
            shakeAnimation.animateTo(-1f, tween(15, easing = easing))
            shakeAnimation.animateTo(0f, tween(15, easing = easing))

            onShakeComplete()
        }

        if (flipRow) {
            val duration = 400
            val easing = LinearEasing
            card1Animation.animateTo(
                -180f,
                tween(durationMillis = duration, easing = easing),
            )
            card2Animation.animateTo(
                -180f,
                tween(durationMillis = duration, easing = easing)
            )
            card3Animation.animateTo(
                -180f,
                tween(durationMillis = duration, easing = easing)
            )
            card4Animation.animateTo(
                -180f,
                tween(durationMillis = duration, easing = easing)
            )
            card5Animation.animateTo(
                -180f,
                tween(durationMillis = duration, easing = easing)
            )

            onFlipRowComplete()
            card1Animation.snapTo(0f)
            card2Animation.snapTo(0f)
            card3Animation.snapTo(0f)
            card4Animation.snapTo(0f)
            card5Animation.snapTo(0f)
        }
    }

    if (flipRow) {
        val pastGuesses = previousGuesses.dropLast(1)
        val lastGuess = previousGuesses.last()

        Column(
            modifier = modifier.aspectRatio(0.82f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (guess in pastGuesses) {
                PreviousGuess(guess = guess)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FlipLetter(
                    letter = lastGuess.firstLetter,
                    result = lastGuess.firstLetterResult,
                    rotation = card1Animation.value,
                    modifier = Modifier.weight(1f),
                )
                FlipLetter(
                    letter = lastGuess.secondLetter,
                    result = lastGuess.secondLetterResult,
                    rotation = card2Animation.value,
                    modifier = Modifier.weight(1f),
                )
                FlipLetter(
                    letter = lastGuess.thirdLetter,
                    result = lastGuess.thirdLetterResult,
                    rotation = card3Animation.value,
                    modifier = Modifier.weight(1f),
                )
                FlipLetter(
                    letter = lastGuess.fourthLetter,
                    result = lastGuess.fourthLetterResult,
                    rotation = card4Animation.value,
                    modifier = Modifier.weight(1f),
                )
                FlipLetter(
                    letter = lastGuess.fifthLetter,
                    result = lastGuess.fifthLetterResult,
                    rotation = card5Animation.value,
                    modifier = Modifier.weight(1f),
                )
            }
            for (i in 0..futureGuessesRemaining) {
                FutureGuess()
            }
        }
    } else {
        Column(
            modifier = modifier.aspectRatio(0.82f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (guess in previousGuesses) {
                PreviousGuess(guess = guess)
            }
            if (previousGuesses.size < 6) {
                CurrentGuess(
                    guess = currentGuess,
                    modifier = Modifier.offset(x = shakeAnimation.value.dp)
                )
            }
            for (i in 1..futureGuessesRemaining) {
                FutureGuess()
            }
        }
    }
}

@Preview
@Composable
fun GameBoardPreview() {
    GameBoard(
        currentGuess = listOf('A', 'B'),
        previousGuesses = listOf(
            GuessResult(
                'A',
                LetterResult.CORRECT,
                'R',
                LetterResult.INCORRECT,
                'I',
                LetterResult.WRONG_LOCATION,
                'S',
                LetterResult.INCORRECT,
                'E',
                LetterResult.INCORRECT
            )
        )
    )
}
