package com.dladukedev.wordle.game.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.R
import com.dladukedev.wordle.game.domain.ColorThemePreference
import com.dladukedev.wordle.game.ui.Header
import com.dladukedev.wordle.game.ui.HeaderIcon
import com.dladukedev.wordle.game.ui.HeaderText
import com.dladukedev.wordle.theme.Theme

@Composable
fun PreferencesScreen(viewModel: EditPreferencesViewModel, onClickClose: () -> Unit) {
    val preferences = viewModel.currentPreferences.collectAsState(initial = null).value

    val showThemeDropdown = remember {
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
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicText(text = "Color Blind Mode",
                        style = labelStyle,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp))
                    Checkbox(
                        checked = preferences.isColorBlindMode,
                        onCheckedChange = { viewModel.updateIsColorBlindPreference(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Theme.colors.correctLetter,
                            uncheckedColor = Theme.colors.darkOnBackground,
                            checkmarkColor = Theme.colors.onCorrectLetter,
                            disabledColor = Theme.colors.mediumOnBackground,
                            disabledIndeterminateColor = Theme.colors.mediumOnBackground,
                        )
                    )
                }
            }
            item {
                Divider(color = Theme.colors.lightOnBackground)
            }
            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicText(text = "Theme",
                        style = labelStyle,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        Row(modifier = Modifier
                            .border(1.dp, Theme.colors.darkOnBackground)
                            .clickable { showThemeDropdown.value = true }
                            .padding(vertical = 8.dp, horizontal = 16.dp)) {
                            BasicText(text = preferences.colorThemePreference.name, style = TextStyle(color = Theme.colors.darkOnBackground))
                        }
                        DropdownMenu(expanded = showThemeDropdown.value,
                            onDismissRequest = { showThemeDropdown.value = false }) {
                            BasicText(text = ColorThemePreference.System.name,
                                modifier = Modifier
                                    .clickable {
                                    viewModel.updateColorThemePreference(ColorThemePreference.System)
                                    showThemeDropdown.value = false
                                }
                                    .padding(vertical = 8.dp, horizontal = 16.dp))
                            BasicText(text = ColorThemePreference.Dark.name,
                                modifier = Modifier
                                    .clickable {
                                    viewModel.updateColorThemePreference(ColorThemePreference.Dark)
                                    showThemeDropdown.value = false
                                }
                                    .padding(vertical = 8.dp, horizontal = 16.dp))
                            BasicText(text = ColorThemePreference.Light.name,
                                modifier = Modifier.clickable {
                                    viewModel.updateColorThemePreference(ColorThemePreference.Light)
                                    showThemeDropdown.value = false
                                }
                                    .padding(vertical = 8.dp, horizontal = 16.dp))
                        }
                    }


                }
            }
        }
    }
}