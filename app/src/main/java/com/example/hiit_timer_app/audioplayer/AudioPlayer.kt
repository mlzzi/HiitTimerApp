package com.example.hiit_timer_app.audioplayer

interface AudioPlayer {
    fun play()
    fun pause()
    fun stop()
    fun isPlaying(): Boolean
}