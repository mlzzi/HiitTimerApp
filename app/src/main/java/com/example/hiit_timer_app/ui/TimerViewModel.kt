package com.example.hiit_timer_app.ui

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.hiit_timer_app.audioplayer.AndroidAudioPlayer
import com.example.hiit_timer_app.model.TimerType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(
        TimerUiState(
            currentTimerType = TimerType.ACTIVE,
            timeActive = 5,
            timeRest = 6,
            progress = 1f,
            initial = 5,
            current = 5,
            rounds = 1,
            currentRound = 2,
            sound = true,
            vibrate = false,
            countdown = false
        )
    )
    val uiState: StateFlow<TimerUiState> = _uiState

    val player by lazy {
        AndroidAudioPlayer(application)
    }

    fun setTimerToStart() {
        val active = _uiState.value.timeActive
        _uiState.update {
            it.copy(
                initial = active,
                current = active,
                progress = 1f,
                currentRound = 2,
                currentTimerType = TimerType.ACTIVE
            )
        }
    }

    fun updateCurrentTimerType(timerType: TimerType) {
        _uiState.update {
            it.copy(
                currentTimerType = timerType
            )
        }
    }

    fun updateTimeActive(time: Int) {
        _uiState.value = _uiState.value.copy(timeActive = time)
    }

    fun updateRounds(rounds: Int) {
        _uiState.value = _uiState.value.copy(rounds = rounds)
    }

    fun updateCurrentRound() {
        val updatedRounds = _uiState.value.currentRound + 1
        _uiState.value = _uiState.value.copy(currentRound = updatedRounds)
    }

    fun updateCurrent(value: Int) {
        _uiState.value = _uiState.value.copy(current = value)
    }

    fun updateTimeRest(time: Int) {
        _uiState.value = _uiState.value.copy(timeRest = time)
    }

    fun updateSoundEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(sound = enabled)
    }

    fun updateVibrateEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(vibrate = enabled)
    }

    fun updateProgress(progress: Float) {
        _uiState.value = _uiState.value.copy(progress = progress)
    }

    // Handle transition logic when active timer finishes
    @VisibleForTesting
    fun handleTimerTypeFinish(timeSet: Int) {
        _uiState.value = _uiState.value.copy(
            current = timeSet,
            initial = timeSet,
            progress = 1f
        )
    }
}