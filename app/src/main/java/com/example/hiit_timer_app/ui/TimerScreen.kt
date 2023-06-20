package com.example.hiit_timer_app.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

// Composable that renders TimerScreen
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
        // keep track of the countdown state
        var countdown by remember { mutableStateOf(timerUiState.countDown) }
        //keep track of the timer running state
        var isTimerRunning by remember { mutableStateOf(false) }

        Box(
            contentAlignment = Alignment.Center
        ) {
            // Render the active timer animation
            SpinAnimation(
                totalTime = timerUiState.timeActive,
                onTimerChange = { time -> viewModel.updateTimeActive(time) },
                color = Color.Magenta,
                modifier = Modifier.size(200.dp),
                isTimerRunning = isTimerRunning,
                setTimerRunning = { isTimerRunning = it }
            )

            // Render the rest timer animation if the active timer is finished
            if (timerUiState.timeActive == 0L) {
                SpinAnimation(
                    totalTime = timerUiState.timeRest,
                    onTimerChange = { time -> viewModel.updateTimeRest(time) },
                    color = Color.Cyan,
                    modifier = Modifier.size(200.dp),
                    isTimerRunning = isTimerRunning,
                    setTimerRunning = { isTimerRunning = it }
                )
            }
            // Render countdown if is on, or the timer text if countdown is off
            if (countdown) {
                AnimationCountDown(
                    onCountDown = { countdown = it },
                    onTimerRunningChange = { isTimerRunning = it }
                )
            } else {
                // Render the formatted time based on the active or rest timer
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

// Animation for when the Countdown is On
@Composable
fun AnimationCountDown(
    onCountDown: (Boolean) -> Unit,
    onTimerRunningChange: (Boolean) -> Unit
) {
    Column() {
        // Count down that goes from 3 to 1
        var count by remember { mutableIntStateOf(3) }

        LaunchedEffect(key1 = count) {
            if (count > 0) {
                delay(1000)
                count--
            } else if (count == 0) {
                // Starts the timer animation after countdown
                onTimerRunningChange(true)
                onCountDown(false)
            }
        }
        AnimatedContent(targetState = count) { targetCount ->
            if (count > 0) {
                // Displays the countdown value
                Text(
                    text = "$targetCount",
                    fontSize = 96.sp,
                    color = Color.White
                )
            }
        }
    }
}

// Function responsible for the timer and spin animation
@Composable
fun SpinAnimation(
    onTimerChange: (Long) -> Unit,
    setTimerRunning: (Boolean) -> Unit,
    isTimerRunning: Boolean,
    totalTime: Long,
    color: Color,
    modifier: Modifier = Modifier
) {
    val initialValue by remember { mutableLongStateOf(totalTime) }
    var currentTime by remember { mutableLongStateOf(totalTime) }
    var progress by remember { mutableFloatStateOf(1f) }

    val progressAnimate by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = currentTime.toInt() * 1000,//animation duration
            easing = LinearEasing
        )
    )
    // Circular animation of the timer
    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier.size(size = 300.dp),
        color = Color.DarkGray,
        strokeWidth = 10.dp,
        strokeCap = StrokeCap.Round,
    )
    // Grey background of the indicator
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

    // Button that stops and play the timer and the spin animation
    IconButton(
        onClick = {
            setTimerRunning(!isTimerRunning)
            progress = totalTime / initialValue.toFloat()
        },
        modifier = Modifier.padding(top = 400.dp),
    ) {
        Icon(
            imageVector = if (isTimerRunning) {
                Icons.Outlined.PauseCircle
            } else {
                Icons.Outlined.PlayCircle
            },
            contentDescription = "",
            tint = if (isTimerRunning) {
                Color.Magenta
            } else {
                   Color.White
                   },
            modifier = modifier.size(50.dp)
        )
    }
}