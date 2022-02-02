package com.dladukedev.wordle.game.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dladukedev.wordle.LocalToastStore
import com.dladukedev.wordle.game.state.GameEvent
import com.dladukedev.wordle.game.state.GameViewModel
import com.dladukedev.wordle.game.state.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    onClickClose: () -> Unit,
    modifier: Modifier = Modifier,
    toastStore: ToastStore = LocalToastStore.current,
    context: Context = LocalContext.current,
    onHowToPlayClick: () -> Unit,
    onPlayAgainRequested: (() -> Unit)? = null,
    viewModel: GameViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState(UIState.Loading).value
    val statistics = viewModel.statistics.collectAsState().value
    val shakeCurrentRow = remember { mutableStateOf(false) }
    val flipCurrentRow = remember { mutableStateOf(false) }
    val resultGuessCount = remember { mutableStateOf<Int?>(null) }
    val gameOverModalShown = remember { mutableStateOf(false) }

    if (state !is UIState.Content) {
        // Loading, fast enough to show nothing
        return Box {}
    }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { value ->
            when (value) {
                is GameEvent.GameOverLoss -> {
                    delay(3000L)
                    toastStore.addToast(value.answer.uppercase())
                    delay(1500L)
                    gameOverModalShown.value = true
                }
                is GameEvent.GameOverWin -> {
                    delay(3000L)
                    val winMessage = when (value.guessCount) {
                        1 -> "Genius"
                        2 -> "Magnificent"
                        3 -> "Impressive"
                        4 -> "Splendid"
                        5 -> "Great"
                        6 -> "Phew"
                        else -> "Invalid Guess Count"
                    }
                    toastStore.addToast(winMessage)
                    delay(1500L)
                    resultGuessCount.value = value.guessCount
                    gameOverModalShown.value = true
                }
                GameEvent.GuessInvalidWord -> {
                    shakeCurrentRow.value = true
                    this.launch {
                        toastStore.addToast("Not in word list")
                    }
                }
                GameEvent.GuessTooShort -> {
                    shakeCurrentRow.value = true
                    this.launch {
                        toastStore.addToast("Not Enough Letters")
                    }
                }
                GameEvent.GuessSuccessfulSubmit -> {
                    flipCurrentRow.value = true
                }
            }
        }.launchIn(this)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            toastStore.clearToasts()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GameHeader(
            isComplete = state.isComplete,
            onClickClose = { onClickClose() },
            onClickShare = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, viewModel.getShareString())
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            },
            onClickHelp = { onHowToPlayClick() },
            modifier = Modifier.height(48.dp),
        )
        GameBoard(
            currentGuess = state.currentInput,
            previousGuesses = state.previousGuesses,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            shakeCurrentRow = shakeCurrentRow.value,
            onShakeComplete = {
                shakeCurrentRow.value = false
            },
            flipRow = flipCurrentRow.value,
            onFlipRowComplete = {
                flipCurrentRow.value = false
                viewModel.enableActions()
            }
        )
        Keyboard(
            letterStates = state.letterStates,
            onLetterSelected = { letter -> viewModel.addLetterToGuess(letter) },
            onDeleteSelected = { viewModel.deleteLetterFromGuess() },
            onEnterSelected = { viewModel.submitGuess() },
            modifier = Modifier
                .defaultMinSize(128.dp)
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        )
        if (gameOverModalShown.value) {
            GameOverModal(
                statistics = statistics,
                onDismissRequested = { gameOverModalShown.value = false },
                onShareRequested = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, viewModel.getShareString())
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                },
                onPlayAgainRequested = onPlayAgainRequested,
                recentResult = resultGuessCount.value,
            )
        }
    }
}