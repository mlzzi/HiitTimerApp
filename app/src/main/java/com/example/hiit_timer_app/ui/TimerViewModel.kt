package com.example.hiit_timer_app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        TimerUiState(
            timeActive = 120L,
            timeRest = 240L,
            rounds = 6,
            sound = true,
            vibrate = false,
            countDown = true
        )
    )
    val uiState: StateFlow<TimerUiState> = _uiState

    fun updateSoundEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(sound = enabled)
    }

    fun updateVibrateEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(vibrate = enabled)
    }

    fun updateCountdownEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(countDown = enabled)
    }

    fun updateActiveTime(time: Long) {
        _uiState.value.timeActive = time
    }

    fun formatTime(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}
