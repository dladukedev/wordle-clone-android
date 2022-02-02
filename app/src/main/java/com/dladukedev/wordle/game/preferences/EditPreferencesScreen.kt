package com.dladukedev.wordle.game.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.domain.ColorThemePreference
import com.dladukedev.wordle.game.ui.Header
import com.dladukedev.wordle.game.ui.HeaderIcon
import com.dladukedev.wordle.game.ui.HeaderText
import com.dladukedev.wordle.theme.Theme

@Composable
fun ThemePreferenceSelectionRow(
    themePreference: ColorThemePreference,
    currentThemePreference: ColorThemePreference,
    onSelectRow: (ColorThemePreference) -> Unit,
) {
    val labelStyle = TextStyle(
        fontSize = 16.sp,
        color = Theme.colors.darkOnBackground,
    )

    Row(modifier = Modifier
        .clickable { onSelectRow(themePreference) }
        .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        BasicText(text = themePreference.name,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .weight(1f),
            style = labelStyle
        )
        if (themePreference == currentThemePreference) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = Theme.colors.mediumOnBackground,
                modifier = Modifier
                    .padding(8.dp)
                    .height(32.dp)
                    .width(32.dp)
            )
        }
    }
}

@Composable
fun PreferencesScreen(viewModel: EditPreferencesViewModel, onClickClose: () -> Unit) {
    val preferences = viewModel.currentPreferences.collectAsState(initial = null).value

    val showThemeSelectionDialog = remember {
        mutableStateOf(false)
    }

    val labelStyle = TextStyle(
        fontSize = 16.sp,
        color = Theme.colors.darkOnBackground,
    )

    if (preferences == null) {
        return Box {}
    }

    Column {
        Header(modifier = Modifier.fillMaxWidth()) {
            HeaderIcon(
                id = R.drawable.ic_close, onClick = { onClickClose() }, modifier = Modifier.align(
                    Alignment.CenterStart
                )
            )
            HeaderText(text = "SETTINGS", modifier = Modifier.align(Alignment.Center))
        }
        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 32.dp)) {
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.updateIsColorBlindPreference(!preferences.isColorBlindMode) },
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicText(text = "Color Blind Mode",
                        style = labelStyle,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp))
                    if(preferences.isColorBlindMode) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            tint = Theme.colors.mediumOnBackground,
                            modifier = Modifier
                                .padding(8.dp)
                                .height(32.dp)
                                .width(32.dp)
                        )
                    }
                }
            }
            item {
                Divider(color = Theme.colors.lightOnBackground)
            }
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showThemeSelectionDialog.value = true },
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicText(text = "Theme",
                        style = labelStyle,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp))

                }
            }
        }
        if (showThemeSelectionDialog.value) {
            Dialog(onDismissRequest = { showThemeSelectionDialog.value = false }) {
                Box(modifier = Modifier.background(Theme.colors.background)) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ThemePreferenceSelectionRow(
                            themePreference = ColorThemePreference.System,
                            currentThemePreference = preferences.colorThemePreference,
                            onSelectRow = { pref ->
                                viewModel.updateColorThemePreference(pref)
                                showThemeSelectionDialog.value = false
                            }
                        )
                        Divider(color = Theme.colors.lightOnBackground)
                        ThemePreferenceSelectionRow(
                            themePreference = ColorThemePreference.Dark,
                            currentThemePreference = preferences.colorThemePreference,
                            onSelectRow = { pref ->
                                viewModel.updateColorThemePreference(pref)
                                showThemeSelectionDialog.value = false
                            }
                        )
                        Divider(color = Theme.colors.lightOnBackground)
                        ThemePreferenceSelectionRow(
                            themePreference = ColorThemePreference.Light,
                            currentThemePreference = preferences.colorThemePreference,
                            onSelectRow = { pref ->
                                viewModel.updateColorThemePreference(pref)
                                showThemeSelectionDialog.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}