package com.example.hiit_timer_app.util

import android.content.Context
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.VisibleForTesting
import com.example.hiit_timer_app.R
import com.example.hiit_timer_app.ui.TimerUiState

object TimerUtil {
    fun formatTime(timeInSeconds: Int): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    @VisibleForTesting
    fun calculateSpinProgress(remainingTime: Int, initialTime: Int): Float {
        return 1 - (remainingTime.toFloat() / initialTime.toFloat())
    }

    fun displayRoundsOnTimerScreen(total: Int, current: Int): String {
        return "Round ${current - 1}/${total}"
    }

    fun showWorkoutLength(uiState: TimerUiState): String {
        return ((uiState.timeActive + uiState.timeRest) * uiState.rounds).toString() + " Minutes"
    }

    fun playCountdownSound(context: Context, timerUiState: TimerUiState) {
        val mMediaPlayer = MediaPlayer.create(context, R.raw.countdown_beep)
        mMediaPlayer.start()
        if (!timerUiState.sound) {
            mMediaPlayer.stop()
        }
    }

    fun vibrate(context: Context, timerUiState: TimerUiState) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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
}