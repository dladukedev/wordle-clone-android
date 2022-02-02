package com.dladukedev.wordle.game.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.theme.Theme

@Composable
fun GuessDistributionGraph(
    guess1Count: Int,
    guess2Count: Int,
    guess3Count: Int,
    guess4Count: Int,
    guess5Count: Int,
    guess6Count: Int,
    modifier: Modifier = Modifier,
    highlightGuessCount: Int? = null,
) {

    val max = derivedStateOf {
        listOf(guess1Count, guess2Count, guess3Count, guess4Count, guess5Count, guess6Count).maxOrNull() ?: 1
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        GuessDistributionRow(
            "1",
            guess1Count,
            max.value,
            highlightGuessCount == 1
        )
        GuessDistributionRow(
            "2",
            guess2Count,
            max.value,
            highlightGuessCount == 2
        )
        GuessDistributionRow(
            "3",
            guess3Count,
            max.value,
            highlightGuessCount == 3
        )
        GuessDistributionRow(
            "4",
            guess4Count,
            max.value,
            highlightGuessCount == 4
        )
        GuessDistributionRow(
            "5",
            guess5Count,
            max.value,
            highlightGuessCount == 5
        )
        GuessDistributionRow(
            "6",
            guess6Count,
            max.value,
            highlightGuessCount == 6
        )
    }
}


@Composable
fun GuessDistributionRow(
    guessNumber: String,
    guessCount: Int,
    maxGuessCount: Int,
    highlight: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (highlight) {
        Theme.colors.correctLetter
    } else {
        Theme.colors.lightOnBackground
    }

    val style = TextStyle(
        color = if (highlight) {
            Theme.colors.onCorrectLetter
        } else {
            Theme.colors.darkOnBackground
        },
        fontSize = 14.sp,
    )

    val labelStyle = TextStyle(
        color = Theme.colors.darkOnBackground,
        fontSize = 14.sp,
    )

    Row(modifier = modifier) {
        BasicText(
            text = guessNumber,
            style = labelStyle,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        if (guessCount == 0) {
            Box(modifier = Modifier.background(backgroundColor)) {
                BasicText(
                    text = "0",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 4.dp),
                    style = style
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .weight(guessCount.toFloat())
            ) {
                BasicText(
                    text = guessCount.toString(),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 4.dp),
                    style = style
                )
            }
            if (guessCount < maxGuessCount) {
                Box(modifier = Modifier.weight((maxGuessCount - guessCount).toFloat()))
            }
        }
    }
}