package com.example.hiit_timer_app.util

import java.util.*

object TimerUtil {
    fun formatTime(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

//    fun formatTime(timeInMillis: Long): String {
//        val totalSeconds = timeInMillis / 1000
//        val minutes = totalSeconds / 60
//        val seconds = totalSeconds % 60
//        return "%02d:%02d".format(minutes, seconds)
//    }
}
