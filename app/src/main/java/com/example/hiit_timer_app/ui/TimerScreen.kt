package com.example.hiit_timer_app.ui

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiit_timer_app.util.TimerUtil
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel
) {
    Surface(
        color = Color(0xFF101010),
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
    ) {
        var countdown by remember { mutableStateOf(true) }

        Box(
            contentAlignment = Alignment.Center
        ) {
            SpinAnimation(
                totalTime = timerUiState.timeActive,
                onTimerChange = { time -> viewModel.updateTimeActive(time) },
                color = Color.Magenta,
                modifier = Modifier.size(200.dp)
            )

            if (timerUiState.timeActive == 0L) {
                SpinAnimation(
                    totalTime = timerUiState.timeRest,
                    onTimerChange = { time -> viewModel.updateTimeRest(time) },
                    color = Color.Cyan,
                    modifier = Modifier.size(200.dp)
                )
            }
            if (countdown) {
                AnimationFadeOut(onCountDown = { countdown = it })
            } else {
                Text(
                    text = if (timerUiState.timeActive > 0L) {
                        TimerUtil.formatTime(timerUiState.timeActive)
                    } else {
                        TimerUtil.formatTime(timerUiState.timeRest)
                    },
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AnimationFadeOut(
    onCountDown: (Boolean) -> Unit
) {
    Column() {
        var count by remember { mutableIntStateOf(3) }

        LaunchedEffect(key1 = count) {
            while (count > 0) {
                delay(1000)
                count--
            }
        }
        AnimatedContent(targetState = count) { targetCount ->
            if (count > 0) {
                Text(
                    text = "$targetCount",
                    fontSize = 96.sp,
                    color = Color.White
                )
            } else {
                onCountDown(false)
            }
        }
    }
}

@Composable
fun SpinAnimation(
    onTimerChange: (Long) -> Unit,
    totalTime: Long,
    color: Color,
    modifier: Modifier = Modifier
) {
    val initialValue by remember { mutableLongStateOf(totalTime) }
    var currentTime by remember { mutableLongStateOf(totalTime) }
    var progress by remember { mutableFloatStateOf(1f) }
    var isTimerRunning by remember { mutableStateOf(false) }

    val progressAnimate by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = currentTime.toInt() * 1000,//animation duration
            easing = LinearEasing
        )
    )
    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier.size(size = 300.dp),
        color = Color.DarkGray,
        strokeWidth = 10.dp,
        strokeCap = StrokeCap.Round,
    )
    CircularProgressIndicator(
        progress = progressAnimate,
        modifier = Modifier.size(size = 300.dp),
        color = color,
        strokeWidth = 10.dp,
        strokeCap = StrokeCap.Round,
    )

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            progress = 0f
            while (currentTime > 0) {
                delay(1000)
                currentTime--
                onTimerChange(currentTime)
            }
        }
    }

    // add space between indicator and button
    Spacer(modifier = Modifier.height(height = 16.dp))

    Button(
        onClick = {
            isTimerRunning = !isTimerRunning
            progress = totalTime / initialValue.toFloat()
            Log.d("Test For Animation", "My variable value: $progress")
            Log.d("Test For Animation", "My variable value: $initialValue")
            Log.d("Test For Animation", "My variable value: $currentTime")
        },
        modifier = Modifier.padding(top = 400.dp),
        colors = ButtonDefaults.buttonColors(Color.Magenta)
    ) {
        Text(
            text = "Go",
            color = Color.White
        )
    }
}