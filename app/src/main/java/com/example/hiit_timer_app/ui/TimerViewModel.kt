package com.example.hiit_timer_app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState(
        timeActive = 1000L,
        timeRest = 2000L,
        rounds = 6,
        sound = true,
        vibrate = true,
        countDown = true
    ))
    val uiState: StateFlow<TimerUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value =
            TimerUiState(
                timeActive = 1000L,
                timeRest = 2000L,
                rounds = 6,
                sound = true,
                vibrate = true,
                countDown = true
            )
    }
}