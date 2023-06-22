package com.example.hiit_timer_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.hiit_timer_app.ui.TimerApp
import com.example.hiit_timer_app.ui.TimerUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerApp()
        }
    }
}

@Preview
@Composable
fun TimerAppPreview() {
    val timerUiState = TimerUiState(
        timeActive = 1000L,
        timeRest = 1000L,
        rounds = 6,
        sound = false,
        vibrate = true,
        countdown = true
    )
    TimerApp()
}