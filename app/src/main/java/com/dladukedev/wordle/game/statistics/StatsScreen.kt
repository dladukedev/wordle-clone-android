package com.dladukedev.wordle.game.statistics

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.domain.StatisticsModel
import com.dladukedev.wordle.game.ui.Header
import com.dladukedev.wordle.game.ui.HeaderIcon
import com.dladukedev.wordle.game.ui.HeaderText
import com.dladukedev.wordle.theme.Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Tab(text: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        color = Theme.colors.darkOnBackground,
        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal
    )

    Box(modifier = modifier.padding(16.dp), contentAlignment = Alignment.Center) {
        BasicText(text = text, style = textStyle)
    }
}

object Tabs {
    const val Overview = 0
    const val Daily = 1
    const val Practice = 2
}

@Composable
fun StatisticsTab(statistics: StatisticsModel, modifier: Modifier = Modifier) {
    val topWordsStyle = TextStyle(
        color = Theme.colors.darkOnBackground,
    )

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

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .widthIn(max = 512.dp)
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .widthIn(max = 360.dp),
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
                if (statistics.currentStreak != null) {
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
                }
                if (statistics.longestStreak != null) {
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
            }

            Spacer(modifier = Modifier.height(16.dp))
            BasicText(text = "GUESS DISTRIBUTION", style = headerStyle)
            Spacer(modifier = Modifier.height(8.dp))
            GuessDistributionGraph(
                guess1Count = statistics.guess1Count,
                guess2Count = statistics.guess2Count,
                guess3Count = statistics.guess3Count,
                guess4Count = statistics.guess4Count,
                guess5Count = statistics.guess5Count,
                guess6Count = statistics.guess6Count,
            )
            Spacer(modifier = Modifier.height(16.dp))
            BasicText(text = "TOP WORDS", style = headerStyle)
            Spacer(modifier = Modifier.height(8.dp))
            statistics.topWords.forEach { pair ->
                val (word, count) = pair
                BasicText(text = "$word ($count)", style = topWordsStyle)
            }
        }
    }
}

@Composable
fun LoadingTab(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BasicText(text = "Loading...", style = TextStyle(color = Theme.colors.darkOnBackground))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel, onClickClose: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })

    val stats = viewModel.stats.collectAsState(initial = null).value
    val coroutineScope = rememberCoroutineScope()
    val scrollToTab = remember {
        { pageIndex: Int ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(pageIndex)
            }
        }
    }

    Column {
        Header(modifier = Modifier.fillMaxWidth()) {
            HeaderIcon(
                id = R.drawable.ic_close,
                onClick = onClickClose,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            HeaderText(text = "STATISTICS", modifier = Modifier.align(Alignment.Center))
        }
        Row {
            Tab(
                text = "OVERVIEW",
                isSelected = pagerState.currentPage == Tabs.Overview,
                modifier = Modifier
                    .weight(1f)
                    .clickable { scrollToTab(Tabs.Overview) }
            )
            Tab(
                text = "DAILY",
                isSelected = pagerState.currentPage == Tabs.Daily,
                modifier = Modifier
                    .weight(1f)
                    .clickable { scrollToTab(Tabs.Daily) }
            )
            Tab(
                text = "PRACTICE",
                isSelected = pagerState.currentPage == Tabs.Practice,
                modifier = Modifier
                    .weight(1f)
                    .clickable { scrollToTab(Tabs.Practice) }
            )
        }
        HorizontalPager(state = pagerState) { pageIndex ->
            when (pageIndex) {
                0 -> if (stats != null) StatisticsTab(statistics = stats.overviewStats) else LoadingTab()
                1 -> if (stats != null) StatisticsTab(statistics = stats.dailyChallengeStats) else LoadingTab()
                2 -> if (stats != null) StatisticsTab(statistics = stats.practiceModeStats) else LoadingTab()
            }
        }
    }
}