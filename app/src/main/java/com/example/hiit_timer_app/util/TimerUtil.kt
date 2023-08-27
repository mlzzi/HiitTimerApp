package com.example.hiit_timer_app.util

import android.content.Context
import android.media.MediaPlayer
import com.example.hiit_timer_app.R
import com.example.hiit_timer_app.ui.TimerUiState
import kotlinx.coroutines.delay

object TimerUtil {
    fun formatTime(timeInSeconds: Int): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    fun calculateSpinProgress(totalTime: Int, initialValue: Int): Float {
        return totalTime / initialValue.toFloat()
    }

    fun displayRoundsOnTimerScreen(total: Int, current: Int): String {
           return "Round ${current - 1}/${total}"
    }

    fun showWorkoutLength(uiState: TimerUiState): String {
        return ((uiState.timeActive + uiState.timeRest) * uiState.rounds).toString() + " Minutes"
    }

    fun playCountdownSound(context: Context) {
        val mMediaPlayer = MediaPlayer.create(context, R.raw.countdown_beep)
        mMediaPlayer.start()
    }
}