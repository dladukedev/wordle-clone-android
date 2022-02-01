package com.dladukedev.wordle.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dladukedev.wordle.R
import com.dladukedev.wordle.theme.Theme

@Composable
fun HelpModal(onDismissRequested: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequested) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
        ) {
            Box() {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = Theme.colors.mediumOnBackground,
                    modifier = Modifier
                        .height(12.dp)
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clickable { onDismissRequested() }
                        .align(Alignment.CenterStart)
                )
                BasicText(text = "HOW TO PLAY")
            }
            LazyColumn {
                item {
                    BasicText(text = "Guess the WORDLE in 6 tries.")
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "Each guess must be a valid 5 letter word. Hit the enter button to submit.")
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "After each guess, the color of the tiles will change to show how close your guess was to the word.")
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Divider(color = Theme.colors.lightOnBackground)
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "Examples")
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    BasicText(text = "TODO ROW")
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "The letter W is in the word and in the correct spot.")
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    BasicText(text = "TODO ROW")
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "The letter I is in the word but in the wrong spot.")
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    BasicText(text = "TODO ROW")
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "The letter U is not in the word in any spot.")
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Divider(color = Theme.colors.lightOnBackground)
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    BasicText(text = "A new WORDLE will be available each day!")
                }
            }
        }
    }
}
