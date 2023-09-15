package com.example.hiit_timer_app

import android.content.Context
import android.media.MediaPlayer
import com.example.hiit_timer_app.ui.TimerUiState
import kotlinx.coroutines.flow.StateFlow

class SoundManager(private val context: Context, uiState: StateFlow<TimerUiState>) {
    var mediaPlayer: MediaPlayer? = null

    fun startCountdownSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.countdown_beep)
        }
        mediaPlayer?.start()
    }

    fun pauseCountdownSound() {
        mediaPlayer?.pause()
    }

    fun stopCountdownSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
