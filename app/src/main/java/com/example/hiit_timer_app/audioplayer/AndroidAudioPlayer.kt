package com.example.hiit_timer_app.audioplayer

import android.content.Context
import android.media.MediaPlayer
import com.example.hiit_timer_app.R

class AndroidAudioPlayer(
    context: Context
) : AudioPlayer {
    private var player: MediaPlayer = MediaPlayer.create(context, R.raw.countdown_beep)

    override fun play() {
        player.start()
    }

    override fun pause() {
        player.pause()
    }

    override fun stop() {
        player.stop()
    }

    override fun isPlaying(): Boolean {
        return player.isPlaying
    }
}