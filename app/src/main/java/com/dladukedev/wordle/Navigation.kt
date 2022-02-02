package com.dladukedev.wordle

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dladukedev.wordle.game.preferences.EditPreferencesViewModel
import com.dladukedev.wordle.game.preferences.PreferencesScreen
import com.dladukedev.wordle.game.state.DailyChallengeGameViewModel
import com.dladukedev.wordle.game.state.PracticeGameViewModel
import com.dladukedev.wordle.game.statistics.StatisticsScreen
import com.dladukedev.wordle.game.statistics.StatisticsViewModel
import com.dladukedev.wordle.game.ui.GameScreen
import com.dladukedev.wordle.game.ui.HowToPlayScreen
import com.dladukedev.wordle.game.ui.ToastStore
import com.dladukedev.wordle.menu.MainScreen

sealed class Routes(val id: String) {
    object MainMenu : Routes("main-menu-route")
    object DailyChallenge : Routes("daily-challenge-route")
    object PracticeMode : Routes("practice-mode-route")
    object Settings : Routes("settings-route")
    object Statistics : Routes("statistics-route")
    object HowToPlay : Routes("how-to-play-route")
}

@Composable
fun Router(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MainMenu.id,
        modifier = modifier
    ) {
        composable(Routes.MainMenu.id) {
            MainScreen(
                onDailyChallengeSelected = { navController.navigate(Routes.DailyChallenge.id) },
                onNewGameSelected = { navController.navigate(Routes.PracticeMode.id) },
                onStatisticsClicked = { navController.navigate(Routes.Statistics.id) },
                onSettingSelected = { navController.navigate(Routes.Settings.id) },
            )
        }
        composable(Routes.DailyChallenge.id) {
            val viewModel = hiltViewModel<DailyChallengeGameViewModel>()
            GameScreen(
                onClickClose = { navController.popBackStack() },
                onHowToPlayClick = { navController.navigate(Routes.HowToPlay.id) },
                viewModel = viewModel
            )
        }
        composable(Routes.PracticeMode.id) {
            val viewModel = hiltViewModel<PracticeGameViewModel>()
            GameScreen(
                onClickClose = { navController.popBackStack() },
                onHowToPlayClick = { navController.navigate(Routes.HowToPlay.id) },
                onPlayAgainRequested = {
                    navController.navigate(
                        Routes.PracticeMode.id,
                        NavOptions.Builder()
                            .setPopUpTo(Routes.PracticeMode.id, inclusive = true)
                            .build()
                    )
                },
                viewModel = viewModel
            )
        }
        composable(Routes.Statistics.id) {
            val viewModel = hiltViewModel<StatisticsViewModel>()

            StatisticsScreen(viewModel, onClickClose = { navController.popBackStack() })
        }
        composable(Routes.Settings.id) {
            val viewModel = hiltViewModel<EditPreferencesViewModel>()

            PreferencesScreen(viewModel, onClickClose = { navController.popBackStack() })
        }
        composable(Routes.HowToPlay.id) {
            HowToPlayScreen {
                navController.popBackStack()
            }
        }
    }
}