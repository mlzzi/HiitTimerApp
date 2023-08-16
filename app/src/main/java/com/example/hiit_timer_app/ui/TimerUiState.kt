package com.example.hiit_timer_app.ui

data class TimerUiState(
    var timeActive: Long,
    var timeRest: Long,
    var progress: Float,
    var rounds: Int,
    var sound: Boolean,
    var vibrate: Boolean,
    var countdown: Boolean,
    val current: Long,
    val initial: Long,
)