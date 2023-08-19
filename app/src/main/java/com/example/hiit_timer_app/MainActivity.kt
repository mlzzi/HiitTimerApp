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
            TimerApp(
                TimerUiState(
                    timeActive = 5L,
                    timeRest = 10L,
                    progress = 1f,
                    rounds = 6,
                    currentRound = 1,
                    sound = true,
                    vibrate = false,
                    countdown = false,
                    current = 5L,
                    initial = 5L,
                )
            )
        }
    }
}

@Preview
@Composable
fun TimerAppPreview() {
    TimerApp(
        TimerUiState(
            timeActive = 5L,
            timeRest = 10L,
            progress = 1f,
            rounds = 6,
            currentRound = 1,
            sound = true,
            vibrate = false,
            countdown = false,
            current = 5L,
            initial = 5L,
        )
    )
}