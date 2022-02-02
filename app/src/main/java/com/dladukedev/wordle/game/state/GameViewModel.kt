package com.dladukedev.wordle.game.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dladukedev.wordle.di.UseDailyChallengeActionStub
import com.dladukedev.wordle.di.UsePracticeActionStub
import com.dladukedev.wordle.game.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class LetterResult {
    CORRECT,
    WRONG_LOCATION,
    INCORRECT,
}


sealed class UIState {
    object Loading : UIState()
    data class Content(
        val currentInput: List<Char> = emptyList(),
        val previousGuesses: List<GuessResult> = emptyList(),
        val letterStates: Map<Char, LetterResult> = emptyMap(),
        val isComplete: Boolean = false,
    ) : UIState()
}

interface GameActionStub {
    fun getName(): String
    suspend fun getInitialState(): GameState
    suspend fun saveGameState(state: GameState)
    suspend fun saveStats(state: GameState)
    suspend fun loadStats(): SummaryStatistics
}

class DailyChallengeGameActionStub @Inject constructor(
    getDateForDailyChallenge: GetDateForDailyChallengeUseCase,
    private val getDailyChallengeWordInitialState: GetDailyChallengeWordInitialState,
    private val getDailyChallengeGameName: GetDailyChallengeGameNameUseCase,
    private val saveDailyChallengeStateUseCase: SaveDailyChallengeStateUseCase,
    private val saveDailyChallengeStatistics: SaveDailyChallengeStatisticsUseCase,
    private val getDailyChallengeStatisticsSummary: GetDailyChallengeStatisticsSummaryUseCase,
    ) : GameActionStub {
    private val challengeDate = getDateForDailyChallenge()

    override fun getName(): String {
        return getDailyChallengeGameName(challengeDate)
    }

    override suspend fun getInitialState(): GameState {
        return getDailyChallengeWordInitialState(challengeDate)
    }

    override suspend fun saveGameState(state: GameState) {
        saveDailyChallengeStateUseCase(challengeDate, state)
    }

    override suspend fun saveStats(state: GameState) {
        saveDailyChallengeStatistics(challengeDate, state)
    }

    override suspend fun loadStats(): SummaryStatistics {
        return getDailyChallengeStatisticsSummary(challengeDate)
    }
}

class PracticeGameActionStub @Inject constructor(
    private val getPracticeModeInitialState: GetPracticeModeInitialStateUseCase,
    private val savePracticeModeState: SavePracticeModeStateUseCase,
    private val getPracticeModeGameName: GetPracticeModeGameNameUseCase,
    private val savePracticeModeStatistics: SavePracticeModeStatisticsUseCase,
    private val getPracticeModeStatisticsSummary: GetPracticeModeStatisticsSummaryUseCase
    ): GameActionStub {
    override fun getName(): String {
        return getPracticeModeGameName()
    }

    override suspend fun getInitialState(): GameState {
        return getPracticeModeInitialState()
    }

    override suspend fun saveGameState(state: GameState) {
        savePracticeModeState(state)
    }

    override suspend fun saveStats(state: GameState) {
        savePracticeModeStatistics(state)
    }

    override suspend fun loadStats(): SummaryStatistics {
        return getPracticeModeStatisticsSummary()
    }
}

@HiltViewModel
class DailyChallengeGameViewModel @Inject constructor(
    @UseDailyChallengeActionStub gameActionStub: GameActionStub,
    wordValidator: GuessWordValidator,
    getShareString: GetShareStringUseCase,
) : GameViewModel(gameActionStub, wordValidator, getShareString)

@HiltViewModel
class PracticeGameViewModel @Inject constructor(
    @UsePracticeActionStub gameActionStub: GameActionStub,
    wordValidator: GuessWordValidator,
    getShareString: GetShareStringUseCase,
) : GameViewModel(gameActionStub, wordValidator, getShareString)


open class GameViewModel(
    private val gameActionStub: GameActionStub,
    private val wordValidator: GuessWordValidator,
    private val getShareString: GetShareStringUseCase,
) : ViewModel() {
    private val gameState = MutableStateFlow<GameState?>(null)
    private val _statistics = MutableStateFlow<SummaryStatistics?>(null)
    val statistics: StateFlow<SummaryStatistics?> = _statistics

    val state: Flow<UIState> = gameState.map {
        if (it == null) {
            UIState.Loading
        } else {
            preventActions = it.isComplete
            mapGameStateToUIState(it)
        }
    }

    private val eventsChannel = Channel<GameEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    private var preventActions = true // For Animations

    fun submitGuess() {
        if (preventActions) {
            return
        }

        viewModelScope.launch {
            gameState.getAndUpdate { currentState ->
                if(currentState != null) {

                    // TODO: Doing Validation here doesn't feel great
                    when (wordValidator.validateGuess(currentState.currentInput)) {
                        GuessValidationResult.Valid -> {
                            preventActions = true
                            eventsChannel.send(GameEvent.GuessSuccessfulSubmit)
                            val newState =
                                GameEngine.reduce(currentState, GameEngine.Action.SubmitGuess)

                            launch {
                                gameActionStub.saveGameState(newState)
                            }

                            if (newState.isWin) {
                                eventsChannel.send(GameEvent.GameOverWin(newState.guessCount))
                            }

                            if (newState.isLoss) {
                                eventsChannel.send(GameEvent.GameOverLoss(newState.targetWord))
                            }

                            if(newState.isComplete) {
                                launch {
                                    gameActionStub.saveStats(newState)
                                    loadStats()
                                }
                            }

                            newState
                        }
                        GuessValidationResult.TooShort -> {
                            eventsChannel.send(GameEvent.GuessTooShort)
                            currentState
                        }
                        GuessValidationResult.InvalidWord -> {
                            eventsChannel.send(GameEvent.GuessInvalidWord)
                            currentState
                        }
                    }
                } else {
                    currentState
                }
            }
        }

    }

    fun addLetterToGuess(letter: Char) {
        if (preventActions) {
            return
        }

        gameState.getAndUpdate { currentState ->
            if (currentState != null) {
                GameEngine.reduce(currentState, GameEngine.Action.AddLetter(letter))
            } else {
                currentState
            }
        }
    }

    fun deleteLetterFromGuess() {
        if (preventActions) {
            return
        }

        gameState.getAndUpdate { currentState ->
            if (currentState != null) {
                GameEngine.reduce(currentState, GameEngine.Action.RemoveLetter)
            } else {
                currentState
            }
        }
    }

    fun enableActions() {
        if (gameState.value?.isComplete == false) {
            preventActions = false
        }
    }

    fun getShareString(): String {
        return runBlocking {
             getShareString(
                gameState.value!!,
                gameActionStub.getName(),
            )
        }
    }

    private fun loadStats() {
        viewModelScope.launch {
            _statistics.update { gameActionStub.loadStats() }
        }
    }

    private fun mapGameStateToUIState(gameState: GameState): UIState.Content {
        val guessedLetters = gameState.previousGuesses.flatMap { guess ->
            listOf(
                guess.firstLetter to guess.firstLetterResult,
                guess.secondLetter to guess.secondLetterResult,
                guess.thirdLetter to guess.thirdLetterResult,
                guess.fourthLetter to guess.fourthLetterResult,
                guess.fifthLetter to guess.fifthLetterResult,
            )
        }.groupBy { it.first }
            .map {
                val (letter, pairs) = it

                if (pairs.any { pair -> pair.second == LetterResult.CORRECT }) {
                    letter to LetterResult.CORRECT
                } else if (pairs.any { pair -> pair.second == LetterResult.WRONG_LOCATION }) {
                    letter to LetterResult.WRONG_LOCATION
                } else {
                    letter to LetterResult.INCORRECT
                }
            }.toMap()

        return UIState.Content(
            gameState.currentInput.toList(),
            gameState.previousGuesses,
            guessedLetters,
            gameState.isComplete,
        )
    }

    init {
        viewModelScope.launch {
            val initialState = withContext(Dispatchers.IO) {
                gameActionStub.getInitialState()
            }

            gameState.update { initialState }
        }
    }
}


