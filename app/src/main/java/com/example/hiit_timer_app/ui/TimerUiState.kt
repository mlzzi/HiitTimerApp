package com.example.hiit_timer_app.ui

data class TimerUiState(
    val timeActive: Long,
    val timeRest: Long,
    val rounds: Int,
    val sound: Boolean,
    val vibrate: Boolean,
    val countDown: Boolean
)