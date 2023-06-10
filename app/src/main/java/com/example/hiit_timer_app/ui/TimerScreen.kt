package com.example.hiit_timer_app.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun TimerScreen(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel = remember { TimerViewModel() }
) {
    CountdownTimerScreen(timerUiState.timeActive, viewModel)
}

@Composable
fun CountdownTimerScreen(time: Long, viewModel: TimerViewModel) {
    val remainingTime = remember { mutableLongStateOf(time) }

    LaunchedEffect(true) {
        while (isActive) {
            if (remainingTime.value > 0) {
                delay(1000) // Delay for 1 second+
                remainingTime.value--
            } else {
                break
            }
        }
    }

    Text(text = viewModel.formatTime(remainingTime.value))
}
