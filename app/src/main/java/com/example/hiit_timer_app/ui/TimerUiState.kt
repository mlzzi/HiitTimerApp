package com.example.hiit_timer_app.ui

data class TimerUiState(
    var timeActive: Long,
    var timeRest: Long,
    var rounds: Int,
    var sound: Boolean,
    var vibrate: Boolean,
    var countDown: Boolean
)