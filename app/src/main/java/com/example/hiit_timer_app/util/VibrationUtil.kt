package com.example.hiit_timer_app.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.example.hiit_timer_app.ui.TimerUiState

//object AndroidVibrator {
//
//    private val vibrator: Vibrator? = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
//
//    fun vibrate(vibrate: Boolean) {
//
//        val pattern = longArrayOf(0, 500, 500, 500, 500, 500, 500, 1000)
//        val amplitude = intArrayOf(
//            0,
//            VibrationEffect.DEFAULT_AMPLITUDE,
//            0,
//            VibrationEffect.DEFAULT_AMPLITUDE,
//            0,
//            VibrationEffect.DEFAULT_AMPLITUDE,
//            0,
//            VibrationEffect.DEFAULT_AMPLITUDE
//        )
//        val repeatIndex = -1 // Repeat the pattern once
//
//        val vibrationEffect1: VibrationEffect =
//            VibrationEffect.createWaveform(pattern, amplitude, repeatIndex)
//
//        if (vibrate) vibrator?.vibrate(vibrationEffect1) else vibrator?.cancel()
//    }
//}