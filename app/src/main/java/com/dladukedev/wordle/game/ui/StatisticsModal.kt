package com.dladukedev.wordle.game.ui

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.domain.SummaryStatistics
import com.dladukedev.wordle.theme.Theme
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

@Composable
fun Countdown(
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
) {
    val timeUntilNext = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val now = LocalDateTime.now()
            val tomorrow = now.plusDays(1).toLocalDate().atStartOfDay()
            val seconds = now.until(tomorrow, ChronoUnit.SECONDS)

            timeUntilNext.value = DateUtils.formatElapsedTime(seconds)
            delay(950)
        }
    }

    BasicText(text = timeUntilNext.value, style = style, modifier = modifier)
}

@Composable
fun StatisticsModalButton(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Theme.colors.correctLetter)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        content()
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameOverModal(
    statistics: SummaryStatistics?,
    onDismissRequested: () -> Unit,
    onShareRequested: () -> Unit,
    modifier: Modifier = Modifier,
    onPlayAgainRequested: (() -> Unit)? = null,
    recentResult: Int? = null
) {
    val headerStyle = TextStyle(
        color = Theme.colors.darkOnBackground,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )

    val largeTextStyle = TextStyle(
        color = Theme.colors.darkOnBackground,
        fontSize = 36.sp,
        textAlign = TextAlign.Center,
    )

    val subheadStyle = TextStyle(
        color = Theme.colors.darkOnBackground,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
    )

    val max = derivedStateOf {
        listOfNotNull(
            statistics?.guess1Count,
            statistics?.guess2Count,
            statistics?.guess3Count,
            statistics?.guess4Count,
            statistics?.guess5Count,
            statistics?.guess6Count,
        ).maxOrNull() ?: 1
    }

    Dialog(
        onDismissRequest = onDismissRequested,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Workaround for resizing
    ) {
        Box(modifier = modifier.padding(horizontal = 32.dp, vertical = 64.dp)) {
            Box(
                modifier = modifier
                    .background(Theme.colors.background)
                    .padding(16.dp)
            ) {
                if (statistics == null) {
                    BasicText(text = "LOADING...")
                } else {
                    Column {
                        BasicText(
                            text = "STATISTICS",
                            style = headerStyle,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier
                                .widthIn(max = 360.dp)
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                BasicText(
                                    text = statistics.gamesPlayed.toString(),
                                    style = largeTextStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                BasicText(
                                    text = "Played",
                                    style = subheadStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                // TODO: Refactor
                                BasicText(
                                    text = (statistics.winPercentage * 100).toInt().toString(),
                                    style = largeTextStyle, modifier = Modifier.fillMaxWidth()
                                )
                                BasicText(
                                    text = "Win %",
                                    style = subheadStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                BasicText(
                                    text = statistics.currentStreak.toString(),
                                    style = largeTextStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                BasicText(
                                    text = "Current Streak",
                                    style = subheadStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                BasicText(
                                    text = statistics.longestStreak.toString(),
                                    style = largeTextStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                BasicText(
                                    text = "Max Streak",
                                    style = subheadStyle,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Box(modifier = Modifier.height(16.dp))

                        BasicText(
                            text = "GUESS DISTRIBUTION",
                            style = headerStyle,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(modifier = Modifier.height(8.dp))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            GuessDistributionRow(
                                "1",
                                statistics.guess1Count,
                                max.value,
                                recentResult == 1
                            )
                            GuessDistributionRow(
                                "2",
                                statistics.guess2Count,
                                max.value,
                                recentResult == 2
                            )
                            GuessDistributionRow(
                                "3",
                                statistics.guess3Count,
                                max.value,
                                recentResult == 3
                            )
                            GuessDistributionRow(
                                "4",
                                statistics.guess4Count,
                                max.value,
                                recentResult == 4
                            )
                            GuessDistributionRow(
                                "5",
                                statistics.guess5Count,
                                max.value,
                                recentResult == 5
                            )
                            GuessDistributionRow(
                                "6",
                                statistics.guess6Count,
                                max.value,
                                recentResult == 6
                            )
                        }

                        Box(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.height(IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 8.dp),
                            ) {
                                if (onPlayAgainRequested == null) {
                                    BasicText(text = "NEXT WORDLE", style = headerStyle)
                                    Countdown(style = largeTextStyle)
                                } else {
                                    StatisticsModalButton(modifier.clickable { onPlayAgainRequested() }) {
                                        BasicText(
                                            text = "PLAY AGAIN",
                                            style = TextStyle(
                                                color = Theme.colors.onCorrectLetter,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        )
                                    }
                                }
                            }
                            Box(Modifier.weight(1f))
                            Box(
                                Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .background(color = Theme.colors.darkOnBackground)
                            )
                            Box(Modifier.weight(1f))
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 8.dp),
                            ) {
                                StatisticsModalButton(modifier = Modifier.clickable { onShareRequested() }) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        BasicText(
                                            text = "SHARE",
                                            style = TextStyle(
                                                color = Theme.colors.onCorrectLetter,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_share),
                                            contentDescription = null,
                                            tint = Theme.colors.onCorrectLetter,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}