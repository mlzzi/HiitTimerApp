package com.example.hiit_timer_app.ui

import com.example.hiit_timer_app.model.TimerType

data class TimerUiState(
    var timeActive: Int,
    var timeRest: Int,
    val currentTimerType: TimerType,
    var progress: Float,
    var rounds: Int,
    var currentRound: Int,
    var sound: Boolean,
    var vibrate: Boolean,
    var countdown: Boolean,
    val current: Int,
    val initial: Int,
)