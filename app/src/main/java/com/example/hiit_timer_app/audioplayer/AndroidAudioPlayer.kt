package com.example.hiit_timer_app.audioplayer

import android.content.Context
import android.media.MediaPlayer
import com.example.hiit_timer_app.R

class AndroidAudioPlayer(
    private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null

    private fun initializePlayer() {
        releasePlayer()
        player = MediaPlayer.create(context, R.raw.countdown_beep)
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun play() {
        if (player == null) {
            initializePlayer()
        }
        player?.start()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        // Resetting the player is necessary to prepare it for the next start
        player?.reset()
    }

    override fun isPlaying(): Boolean {
        return player?.isPlaying == true
    }

    // Release resources when the player is no longer needed
    fun release() {
        releasePlayer()
    }
}
