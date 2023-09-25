package com.example.hiit_timer_app

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.hiit_timer_app.ui.theme.HiitTimerAppTheme
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.ui.TimerApp
import com.example.hiit_timer_app.ui.TimerUiState

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            0
        )
        setContent {
            HiitTimerAppTheme {
                TimerApp(
                    TimerUiState(
                        timeActive = 5,
                        timeRest = 10,
                        currentTimerType = TimerType.ACTIVE,
                        progress = 1f,
                        rounds = 6,
                        currentRound = 1,
                        sound = true,
                        vibrate = false,
                        countdown = false,
                        current = 5,
                        initial = 5
                    ),
                    context = this@MainActivity
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerAppPreview() {
    TimerApp(
        TimerUiState(
            timeActive = 5,
            timeRest = 10,
            currentTimerType = TimerType.ACTIVE,
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