package com.example.hiit_timer_app.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.util.TimerUtil
import kotlinx.coroutines.delay

@Composable
fun Animation(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    countdown: Boolean,
    isTimerRunning: Boolean,
    changeCountdown: (Boolean) -> Unit,
    changeTimerRunning: (Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {

        SelectTimerType(
            timerUiState = timerUiState,
            viewModel = viewModel,
            isTimerRunning = isTimerRunning
        )

        // Render countdown if is on, or the timer text if countdown is off
        if (countdown) {
            AnimationCountDown(
                onCountDown = { changeCountdown(it) },
                onTimerRunningChange = { changeTimerRunning(it) }
            )
        } else {
            // Render the formatted time based on the active or rest timer
            Text(
                text = TimerUtil.formatTime(timerUiState.current),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SelectTimerType(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    isTimerRunning: Boolean
) {
    if (timerUiState.rounds > 0) {
        // Render the rest timer animation if the active timer is finished
        if (timerUiState.currentTimerType == TimerType.ACTIVE) {
            // Render the active timer animation
            SpinAnimation(
                viewModel = viewModel,
                uiState = timerUiState,
                color = Color.Magenta,
                modifier = Modifier.size(200.dp),
                isTimerRunning = isTimerRunning
            )
            if (timerUiState.current == 0L) {
                viewModel.handleActiveTimerFinished(timerUiState.timeRest) // Handle transition in the view model
                viewModel.updateCurrentTimerType(TimerType.REST)
            }
        }
        if (timerUiState.currentTimerType == TimerType.REST){
            // Render the active timer animation
            SpinAnimation(
                viewModel = viewModel,
                uiState = timerUiState,
                color = Color.Cyan,
                modifier = Modifier.size(200.dp),
                isTimerRunning = isTimerRunning
            )
            if (timerUiState.current == 0L) {
                viewModel.handleActiveTimerFinished(timerUiState.timeActive) // Handle transition in the view model
                viewModel.updateCurrentTimerType(TimerType.ACTIVE)
                viewModel.updateRounds(- 1)
            }
        }
    } else {
        viewModel.updateCurrentTimerType(TimerType.FINISH)
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.size(size = 300.dp),
            color = Color.DarkGray,
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}

// Function responsible for the timer and spin animation
@Composable
fun SpinAnimation(
    uiState: TimerUiState,
    viewModel: TimerViewModel,
    isTimerRunning: Boolean,
    color: Color,
    modifier: Modifier = Modifier
) {
    val progressAnimate by animateFloatAsState(
        targetValue = uiState.progress,
        animationSpec = tween(
            durationMillis = uiState.current.toInt() * 1000, //animation duration
            easing = LinearEasing
        ), label = "SpinAnimation"
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
            val initialTime = uiState.current
            for (remainingTime in initialTime downTo 0) {
                // Update the timer and the progress bar
                viewModel.updateCurrent(remainingTime)
                uiState.progress = 1 - (remainingTime.toFloat() / initialTime.toFloat())
                delay(1000)
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
    Column {
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
        AnimatedContent(targetState = count, label = "CountDown") { targetCount ->
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