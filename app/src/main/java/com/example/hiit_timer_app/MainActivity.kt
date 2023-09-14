package com.example.hiit_timer_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.ui.TimerApp
import com.example.hiit_timer_app.ui.TimerUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerApp(
                TimerUiState(
                    currentTimerType = TimerType.ACTIVE,
                    timeActive = 5,
                    timeRest = 10,
                    progress = 1f,
                    rounds = 6,
                    currentRound = 1,
                    sound = true,
                    vibrate = false,
                    countdown = false,
                    current = 5,
                    initial = 5,
                ),
                context = this@MainActivity
            )
        }
    }
}

@Preview
@Composable
fun TimerAppPreview() {
    TimerApp(
        TimerUiState(
            currentTimerType = TimerType.ACTIVE,
            timeActive = 5,
            timeRest = 10,
            progress = 1f,
            rounds = 6,
            currentRound = 1,
            sound = true,
            vibrate = false,
            countdown = false,
            current = 5,
            initial = 5,
        ),
        context = LocalContext.current
    )
}