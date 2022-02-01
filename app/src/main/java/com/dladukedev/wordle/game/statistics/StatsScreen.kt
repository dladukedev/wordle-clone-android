package com.dladukedev.wordle.game.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun StatisticsScreen() {
    val test = remember { mutableStateOf("TEST VALUE")}
    
    LaunchedEffect(Unit) {
        test.value = "value"
    }
    
    if(test.value == "value") {
        Box(modifier = Modifier.background(Color.Blue).fillMaxSize())
    } else {
        Box(modifier = Modifier.background(Color.Red).fillMaxSize())
    }
}