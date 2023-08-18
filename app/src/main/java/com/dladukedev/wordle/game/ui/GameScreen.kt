package com.dladukedev.wordle.game.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dladukedev.wordle.LocalToastStore
import com.dladukedev.wordle.game.domain.GameState
import com.dladukedev.wordle.game.domain.StatisticsModel
import com.dladukedev.wordle.game.domain.SummaryStatistics
import com.dladukedev.wordle.game.state.GameEvent
import com.dladukedev.wordle.game.state.GameViewModel
import com.dladukedev.wordle.game.state.UIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

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
    val state by viewModel.state.collectAsState(UIState.Loading)
    val statistics by viewModel.statistics.collectAsState()
    val events by viewModel.events.collectAsState()

    GameScreen(
        state,
        statistics,
        events,
        onClickClose,
        modifier,
        toastStore,
        context,
        onHowToPlayClick,
        onPlayAgainRequested,
        viewModel::getShareString,
        viewModel::enableActions,
        viewModel::addLetterToGuess,
        viewModel::deleteLetterFromGuess,
        viewModel::submitGuess,
        viewModel::markEventHandled,
    )
}

@Composable
fun GameScreen(
    state: UIState,
    statistics: SummaryStatistics?,
    events: ImmutableList<GameEvent>,
    onClickClose: () -> Unit,
    modifier: Modifier = Modifier,
    toastStore: ToastStore = LocalToastStore.current,
    context: Context = LocalContext.current,
    onHowToPlayClick: () -> Unit,
    onPlayAgainRequested: (() -> Unit)? = null,
    getShareString: () -> String,
    enableActions: () -> Unit,
    addLetterToGuess: (Char) -> Unit,
    deleteLetterFromGuess: () -> Unit,
    submitGuess: () -> Unit,
    markEventHandled: (UUID) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var hasFocus by remember { mutableStateOf(false) }
    Box(
        Modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                hasFocus = it.hasFocus
            }
            .focusable()
            .onKeyEvent {
                if (it.type == KeyEventType.KeyUp) {
                    when (it.key) {
                        Key.Backspace -> {
                            deleteLetterFromGuess()
                            true
                        }

                        Key.Enter -> {
                            submitGuess()
                            true
                        }

                        Key.A -> {
                            addLetterToGuess('A')
                            true
                        }

                        Key.B -> {
                            addLetterToGuess('B')
                            true
                        }

                        Key.C -> {
                            addLetterToGuess('C')
                            true
                        }

                        Key.D -> {
                            addLetterToGuess('D')
                            true
                        }

                        Key.E -> {
                            addLetterToGuess('E')
                            true
                        }

                        Key.F -> {
                            addLetterToGuess('F')
                            true
                        }

                        Key.G -> {
                            addLetterToGuess('G')
                            true
                        }

                        Key.H -> {
                            addLetterToGuess('H')
                            true
                        }

                        Key.I -> {
                            addLetterToGuess('I')
                            true
                        }

                        Key.J -> {
                            addLetterToGuess('J')
                            true
                        }

                        Key.K -> {
                            addLetterToGuess('K')
                            true
                        }

                        Key.L -> {
                            addLetterToGuess('L')
                            true
                        }

                        Key.M -> {
                            addLetterToGuess('M')
                            true
                        }

                        Key.N -> {
                            addLetterToGuess('N')
                            true
                        }

                        Key.O -> {
                            addLetterToGuess('O')
                            true
                        }

                        Key.P -> {
                            addLetterToGuess('P')
                            true
                        }

                        Key.Q -> {
                            addLetterToGuess('Q')
                            true
                        }

                        Key.R -> {
                            addLetterToGuess('R')
                            true
                        }

                        Key.S -> {
                            addLetterToGuess('S')
                            true
                        }

                        Key.T -> {
                            addLetterToGuess('T')
                            true
                        }

                        Key.U -> {
                            addLetterToGuess('U')
                            true
                        }

                        Key.V -> {
                            addLetterToGuess('V')
                            true
                        }

                        Key.W -> {
                            addLetterToGuess('W')
                            true
                        }

                        Key.X -> {
                            addLetterToGuess('X')
                            true
                        }

                        Key.Y -> {
                            addLetterToGuess('Y')
                            true
                        }

                        Key.Z -> {
                            addLetterToGuess('Z')
                            true
                        }

                        else -> false
                    }
                } else {
                    false
                }
            })

    if (!hasFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    var shakeCurrentRow by remember { mutableStateOf(false) }
    var flipCurrentRow by remember { mutableStateOf(false) }
    var resultGuessCount by remember { mutableStateOf<Int?>(null) }
    var gameOverModalShown by remember { mutableStateOf(false) }

    val onClickShare = remember(getShareString) {
        {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getShareString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }
    val onFlipComplete = remember {
        {
            flipCurrentRow = false
            enableActions()
        }
    }
    val onShakeComplete = remember {
        { shakeCurrentRow = false }
    }

    if (state !is UIState.Content) {
        // Loading, fast enough to show nothing
        return Box {}
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(events) {
        val gameEvent = events.firstOrNull() ?: return@LaunchedEffect

        when (gameEvent) {
            is GameEvent.GameOverLoss -> {
                coroutineScope.launch {
                    delay(3000L)
                    toastStore.addToast(gameEvent.answer.uppercase())
                    delay(1500L)
                    gameOverModalShown = true
                }
            }

            is GameEvent.GameOverWin -> {
                coroutineScope.launch {
                    delay(3000L)
                    val winMessage = when (gameEvent.guessCount) {
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
                    resultGuessCount = gameEvent.guessCount
                    gameOverModalShown = true
                }
            }

            GameEvent.GuessInvalidWord -> {
                shakeCurrentRow = true
                coroutineScope.launch {
                    toastStore.addToast("Not in word list")
                }
            }

            GameEvent.GuessTooShort -> {
                shakeCurrentRow = true
                coroutineScope.launch {
                    toastStore.addToast("Not Enough Letters")
                }
            }

            GameEvent.GuessSuccessfulSubmit -> {
                flipCurrentRow = true
            }
        }

        markEventHandled(gameEvent.id)
    }

    DisposableEffect(Unit) {
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
            onClickClose = onClickClose,
            onClickShare = onClickShare,
            onClickHelp = onHowToPlayClick,
            modifier = Modifier.height(48.dp),
        )
        GameBoard(
            currentGuess = state.currentInput,
            previousGuesses = state.previousGuesses,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            shakeCurrentRow = shakeCurrentRow,
            onShakeComplete = onShakeComplete,
            flipRow = flipCurrentRow,
            onFlipRowComplete = onFlipComplete
        )
        Keyboard(
            letterStates = state.letterStates,
            onLetterSelected = addLetterToGuess,
            onDeleteSelected = deleteLetterFromGuess,
            onEnterSelected = submitGuess,
            modifier = Modifier
                .defaultMinSize(128.dp)
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        )
        if (gameOverModalShown) {
            GameOverModal(
                statistics = statistics,
                onDismissRequested = { gameOverModalShown = false },
                onShareRequested = onClickShare,
                onPlayAgainRequested = onPlayAgainRequested,
                recentResult = resultGuessCount,
            )
        }
    }
}