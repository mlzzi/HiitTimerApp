package com.example.hiit_timer_app.util

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.VisibleForTesting
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
        return formatTime((uiState.timeActive + uiState.timeRest) * uiState.rounds) + " Minutes"
    }

    @Suppress("DEPRECATION")
    fun vibrate(context: Context, timerUiState: TimerUiState, type: Int, repeat: Int) {

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val vibrationEffect1 = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrationEffect2 =
            VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)


        if (type == 1) {
            vibrator.vibrate(vibrationEffect1)
        }
        if (type == 2) {
            vibrator.vibrate(vibrationEffect2)
        }
    }

    fun vibrate(context: Context, vibrator: Vibrator) {
//        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 1000)
        val amplitude = intArrayOf(
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

        vibrator.vibrate(vibrationEffect1)
    }
}