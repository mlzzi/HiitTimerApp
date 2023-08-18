package com.example.hiit_timer_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiit_timer_app.model.TimerType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        TimerUiState(
            timeActive = 2L,
            timeRest = 3L,
            progress = 1f,
            initial = 2L,
            current = 2L,
            rounds = 1,
            sound = true,
            vibrate = false,
            countdown = false
        )
    )
    val uiState: StateFlow<TimerUiState> = _uiState

    fun updateCurrentTimerType(timerType: TimerType) {
        _uiState.update {
            it.copy(
                currentTimerType = timerType
            )
        }
    }

    fun updateTimeActive(time: Long) {
        _uiState.value = _uiState.value.copy(timeActive = time)
    }

    fun updateRounds(change: Int) {
        val updatedRounds = _uiState.value.rounds + change
        _uiState.value = _uiState.value.copy(rounds = updatedRounds)
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
    fun handleActiveTimerFinished(timeSet: Long) {
        _uiState.value = _uiState.value.copy(
            initial = timeSet,
            current = timeSet,
            progress = 1f
        )

    }
}