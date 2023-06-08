package com.example.hiit_timer_app.util

import java.util.*

object TimerUtil {
    fun convertTimeToSeconds(hour: Int, minute: Int, second: Int): Long {
        return (hour * 3600 + minute * 60 + second).toLong()
    }

    fun convertSecondsToTime(time: Long): Time {
        val hours = time / 3600
        val minutes = (time % 3600) / 60
        val seconds = (time % 3600) % 60
        return Time(hours.toInt(), minutes.toInt(), seconds.toInt())
    }
}

data class Time(val hour: Int, val minute: Int, val second: Int)
