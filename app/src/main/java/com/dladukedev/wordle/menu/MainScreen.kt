package com.dladukedev.wordle.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.theme.Theme

@Composable
fun MainScreen(
    onDailyChallengeSelected: () -> Unit,
    onNewGameSelected: () -> Unit,
    onSettingSelected: () -> Unit,
    onStatisticsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "WORDLE",
            fontWeight = FontWeight.Light,
            fontSize = 60.sp,
            color = Theme.colors.darkOnBackground
        )
        Spacer(modifier = Modifier.height(64.dp))
        MainMenuButton(modifier = Modifier.clickable { onDailyChallengeSelected() }) {
            MainMenuButtonText(text = "Daily Challenge")
        }
        Spacer(modifier = Modifier.height(16.dp))

        MainMenuButton(modifier = Modifier.clickable { onNewGameSelected() }) {
            MainMenuButtonText(text = "Practice Mode")
        }
        Spacer(modifier = Modifier.height(16.dp))
        MainMenuButton(modifier = Modifier.clickable { onStatisticsClicked() }) {
            MainMenuButtonText(text = "Statistics")
        }
        Spacer(modifier = Modifier.height(16.dp))
        MainMenuButton(modifier = Modifier.clickable { onSettingSelected() }) {
            MainMenuButtonText(text = "Settings")
        }
    }
}

@Preview
@Composable
fun PreviewMainMenu() {
    MainScreen(
        onDailyChallengeSelected = { /*TODO*/ },
        onNewGameSelected = { /*TODO*/ },
        onStatisticsClicked = { /*TODO*/ },
        onSettingSelected = { /*TODO*/ },
    )
}