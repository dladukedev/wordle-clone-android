package com.dladukedev.wordle.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dladukedev.wordle.LocalToastStore
import com.dladukedev.wordle.theme.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.util.*

data class ToastModel(
    val id: UUID,
    val message: String,
    val duration: Long,
    val isVisible: Boolean = true,
)

class ToastStore {
    private val _toasts = MutableStateFlow<List<ToastModel>>(emptyList())
    val toasts: StateFlow<List<ToastModel>> = _toasts

    private fun removeToast(id: UUID) {
        _toasts.getAndUpdate { currentToasts ->
            currentToasts.map { if(it.id == id) it.copy(isVisible = false) else it }
        }
    }

    suspend fun addToast(message: String, length: Long = 1500L) {
       val newToast = ToastModel(UUID.randomUUID(), message, length)
        _toasts.getAndUpdate { currentToasts ->
            currentToasts + newToast
        }
        withContext(Dispatchers.Main) {
            delay(newToast.duration)
            removeToast(newToast.id)
        }
    }

    fun clearToasts() {
        _toasts.update { emptyList() }
    }
}

@Composable
fun Toast(toast: ToastModel, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = toast.isVisible,
        enter = fadeIn(
            initialAlpha = 0.8f
        ),
        exit = fadeOut(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Theme.colors.toast)
                .padding(16.dp)
        ) {
            Text(text = toast.message, color = Theme.colors.onToast, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ToastContainer(
    modifier: Modifier = Modifier,
    toastStore: ToastStore = LocalToastStore.current
) {
    val toasts = toastStore.toasts.collectAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        for (toast in toasts.value.reversed()) {
            Toast(toast = toast)
        }
    }
}