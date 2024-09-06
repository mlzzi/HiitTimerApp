package com.example.hiit_timer_app.ui

import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import com.example.hiit_timer_app.audioplayer.AndroidAudioPlayer
import com.example.hiit_timer_app.model.TimerType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(
        TimerUiState(
            currentTimerType = TimerType.PREPARE,
            timeActive = 15,
            timeRest = 20,
            progress = 1f,
            initial = 5,
            current = 5,
            rounds = 4,
            currentRound = 2,
            sound = false,
            vibrate = false,
            countdown = false
        )
    )
    val uiState: StateFlow<TimerUiState> = _uiState

    val player by lazy {
        AndroidAudioPlayer(application)
    }

    @Suppress("DEPRECATION")
    private val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun vibrate() {
        val pattern = longArrayOf(0, 500, 500, 500, 500, 500, 500, 1000)
        val amplitude = intArrayOf(
            0,
            VibrationEffect.DEFAULT_AMPLITUDE,
            0,
            VibrationEffect.DEFAULT_AMPLITUDE,
            0,
            VibrationEffect.DEFAULT_AMPLITUDE,
            0,
            VibrationEffect.DEFAULT_AMPLITUDE
        )
        val repeatIndex = -1 // Repeat the pattern once

        val vibrationEffect1: VibrationEffect =
            VibrationEffect.createWaveform(pattern, amplitude, repeatIndex)

        vibrator.cancel()
        vibrator.vibrate(vibrationEffect1)
    }

    fun cancelVibration() {
        vibrator.cancel()
    }

    fun setTimerToStart() {
        val active = _uiState.value.timeActive
        _uiState.update {
            it.copy(
                initial = active,
                current = active,
                progress = 1f,
                currentRound = 2,
                currentTimerType = TimerType.PREPARE
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

/*
lets start by the arquitecture. My files are dispose as this:

com.example.hiit_timer_app
    audioplayer
        AndroidAudioPlayer.kt
        AudioPlayer.kt
    dialog
        Dialog.kt
        NumberPickerDialog.kt
    model
        TimerType.kt
    running
        RunningApp.kt
        RunningService.kt
    ui
        theme
            Color.kt
            Shape.kt
            Theme.kt
            Type.kt
        Animation.kt
        SetupConfiguration.kt
        TimerApp.kt
        TimerScreen.kt
        TimerUiState.kt
        TimerViewModel.kt
    util
        TimerUtil.kt
        VibrationUtil.kt
        Vibrator.kt
    MainActivity.kt

 */