package com.example.hiit_timer_app.util

import com.example.hiit_timer_app.ui.TimerUiState
import com.example.hiit_timer_app.ui.TimerViewModel
import java.util.*

object TimerUtil {
    fun formatTime(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    fun calculateSpinProgress(totalTime: Long, initialValue: Long): Float {
        return totalTime / initialValue.toFloat()
    }

    fun displayRoundsOnTimerScreen(total: Int, current: Int): String {
        if (current <= total) {
            return "Round ${current}/${total}"
        } else {
            return "Round ${current - 1}/${total}"
        }
    }

    fun showWorkoutLength(uiState: TimerUiState): String {
        return ((uiState.timeActive + uiState.timeRest) * uiState.rounds).toString() + " Minutes"
    }
}
