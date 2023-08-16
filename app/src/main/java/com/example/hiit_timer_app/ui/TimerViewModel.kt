package com.example.hiit_timer_app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        TimerUiState(
            timeActive = 5L,
            timeRest = 10L,
            progress = 1f,
            initial = 5L,
            current = 5L,
            rounds = 6,
            sound = true,
            vibrate = false,
            countdown = false
        )
    )
    val uiState: StateFlow<TimerUiState> = _uiState

    fun updateTimeActive(time: Long) {
        _uiState.value = _uiState.value.copy(timeActive = time)
    }

    fun updateInitial(value: Long) {
        _uiState.value = _uiState.value.copy(initial = value)
    }

    fun updateCurrent(value: Long) {
        _uiState.value = _uiState.value.copy(current = value)
    }

    fun updateTimeRest(time: Long) {
        _uiState.value = _uiState.value.copy(timeRest = time)
    }

    fun updateSoundEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(sound = enabled)
    }

    fun updateVibrateEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(vibrate = enabled)
    }

    fun updateCountdownEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(countdown = enabled)
    }

    fun updateProgress(progress: Float) {
        _uiState.value = _uiState.value.copy(progress = progress)
    }

    // Handle transition logic when active timer finishes
    fun handleActiveTimerFinished() {
        val restTime = _uiState.value.timeRest
        _uiState.value = _uiState.value.copy(
            initial = restTime,
            current = restTime,
            progress = 1f
        )

    }
}