package com.dladukedev.wordle.game.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.theme.Theme

@Composable
fun HeaderIcon(@DrawableRes id: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = id),
        contentDescription = null,
        tint = Theme.colors.mediumOnBackground,
        modifier = modifier
            .padding(8.dp)
            .height(32.dp)
            .width(32.dp)
            .clickable { onClick() }
    )
}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier) {
    val style = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        color = Theme.colors.darkOnBackground,
    )

    BasicText(
        text = text,
        style = style,
        modifier = modifier,
    )
}

@Composable
fun Header(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
    ) {
        content()
        Divider(
            color = Theme.colors.lightOnBackground, modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun GameHeader(
    isComplete: Boolean,
    onClickClose: () -> Unit,
    onClickShare: () -> Unit,
    onClickHelp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val initialIsComplete by remember { mutableStateOf(isComplete) }

    Header(modifier = modifier.fillMaxWidth()) {
        HeaderIcon(
            id = R.drawable.ic_close,
            onClick = { onClickClose() },
            modifier = Modifier.align(Alignment.CenterStart)
        )
        HeaderText(text = "WORDLE", modifier = Modifier.align(Alignment.Center))
        Crossfade(
            targetState = isComplete,
            animationSpec = if (initialIsComplete) {
                snap()
            } else {
                tween(150, 2500)
            },
            modifier = Modifier.align(Alignment.CenterEnd),
        ) { targetState ->
            if (targetState) {
                HeaderIcon(
                    id = R.drawable.ic_share,
                    onClick = { onClickShare() },
                )
            } else {
                HeaderIcon(
                    id = R.drawable.ic_help,
                    onClick = { onClickHelp() },
                )
            }

        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    GameHeader(false, {}, {}, {})
}