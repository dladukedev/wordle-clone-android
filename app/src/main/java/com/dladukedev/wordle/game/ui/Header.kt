package com.dladukedev.wordle.game.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.theme.Theme

@Composable
fun Header(
    isComplete: Boolean,
    onClickClose: () -> Unit,
    onClickShare: () -> Unit,
    onClickHelp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val initialIsComplete by remember { mutableStateOf(isComplete) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = Theme.colors.mediumOnBackground,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clickable { onClickClose() }
            )
            Text(
                text = "WORDLE",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                color = Theme.colors.darkOnBackground
            )

            Crossfade(
                targetState = isComplete,
                animationSpec = if (initialIsComplete) {
                    snap()
                } else {
                    tween(150, 2500)
                }
            ) { targetState ->
                if (targetState) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        tint = Theme.colors.mediumOnBackground,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clickable { onClickShare() }
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help),
                        contentDescription = null,
                        tint = Theme.colors.mediumOnBackground,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clickable { onClickHelp() }
                    )
                }
            }
        }

        Divider(color = Theme.colors.lightOnBackground)
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(false, {}, {}, {})
}