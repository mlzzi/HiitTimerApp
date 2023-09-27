package com.example.hiit_timer_app.ui

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiit_timer_app.R
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.util.TimerUtil
import com.example.hiit_timer_app.util.TimerUtil.calculateSpinProgress
import kotlinx.coroutines.delay

@Composable
fun Animation(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    isTimerRunning: Boolean,
    changeTimerRunning: (Boolean) -> Unit,
    context: Context
) {
    // Use a rememberUpdatedState composable function to store the formatted time in memory and to update the UI whenever the formatted time changes.
    val formattedTime by rememberUpdatedState(TimerUtil.formatTime(timerUiState.current))

    // Wrap the entire body of the composable function in a composable function.
    Box(
        contentAlignment = Alignment.Center
    ) {

        SelectTimerType(
            timerUiState = timerUiState,
            viewModel = viewModel,
            isTimerRunning = isTimerRunning,
            context = context
        )
        Text(
            text = formattedTime,
            style = MaterialTheme.typography.displayMedium,
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        // Render countdown if is on, or the timer text if countdown is off
        if (
            timerUiState.current == timerUiState.initial &&
            timerUiState.currentTimerType == TimerType.ACTIVE
        ) {
            if (!isTimerRunning) {
                if (timerUiState.sound) {
                    CountdownBeepPlayer(timerUiState, viewModel, isTimerRunning)
                }
                if (timerUiState.vibrate) {
                    Vibration(timerUiState)
                }
                AnimationCountDown(
                    timerViewModel = viewModel,
                    onTimerRunningChange = { changeTimerRunning(it) }
                )
            }
        }
    }
}

// Composable that deals with timer type change and the number of rounds
@Composable
fun SelectTimerType(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    isTimerRunning: Boolean,
    context: Context
) {
    when (timerUiState.currentTimerType) {
        TimerType.ACTIVE -> {
            SpinAnimation(
                viewModel = viewModel,
                uiState = timerUiState,
                color = Color.Magenta,
                modifier = Modifier.size(dimensionResource(id = R.dimen.animation_size)),
                isTimerRunning = isTimerRunning
            )
            if (timerUiState.current == 3) {
                CountdownBeepPlayer(timerUiState, viewModel, isTimerRunning)
                Vibration(timerUiState)
            }
            if (timerUiState.current == 0) {
                viewModel.handleTimerTypeFinish(timerUiState.timeRest)
                viewModel.updateCurrentTimerType(TimerType.REST)
            }
        }

        TimerType.REST -> {
            SpinAnimation(
                viewModel = viewModel,
                uiState = timerUiState,
                color = Color.Cyan,
                modifier = Modifier.size(dimensionResource(id = R.dimen.animation_size)),
                isTimerRunning = isTimerRunning
            )
            if (timerUiState.current <= 3) {
                CountdownBeepPlayer(timerUiState, viewModel, isTimerRunning)
                Vibration(timerUiState)
            }
            if (timerUiState.current == 0) {
                if (timerUiState.currentRound <= timerUiState.rounds) {
                    viewModel.updateCurrentRound()
                    viewModel.handleTimerTypeFinish(timerUiState.timeActive)
                    viewModel.updateCurrentTimerType(TimerType.ACTIVE)
                } else {
                    viewModel.updateCurrentTimerType(TimerType.FINISH)
                    viewModel.updateCurrent(0)
                }
            }
        }

        else -> {
            CircularProgressIndicator(
                progress = 1f,
                modifier = Modifier.size(size = dimensionResource(id = R.dimen.spin_animation_size)),
                color = Color.DarkGray,
                strokeWidth = dimensionResource(id = R.dimen.stroke_width),
                strokeCap = StrokeCap.Round,
            )
        }
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
            durationMillis = uiState.current * 1000, //animation duration
            easing = LinearEasing
        ), label = "SpinAnimation"
    )
    // Circular animation of the timer
    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier.size(size = dimensionResource(id = R.dimen.spin_animation_size)),
        color = Color.DarkGray,
        strokeWidth = dimensionResource(id = R.dimen.stroke_width),
        strokeCap = StrokeCap.Round,
    )
    // Grey background of the indicator
    CircularProgressIndicator(
        progress = progressAnimate,
        modifier = Modifier.size(size = dimensionResource(id = R.dimen.spin_animation_size)),
        color = color,
        strokeWidth = dimensionResource(id = R.dimen.stroke_width),
        strokeCap = StrokeCap.Round,
    )
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            val initialTime = uiState.current
            for (remainingTime in initialTime downTo 0) {
                // Update the timer and the progress bar
                viewModel.updateCurrent(remainingTime)
                uiState.progress = calculateSpinProgress(remainingTime, initialTime)
                delay(1000)
            }
        }
    }
}

// Animation countdown before timer starts
@Composable
fun AnimationCountDown(
    timerViewModel: TimerViewModel,
    onTimerRunningChange: (Boolean) -> Unit
) {
    val color = MaterialTheme.colorScheme.background
    Box(
        contentAlignment = Alignment.Center
    ) {
        // Count down that goes from 3 to 1
        var count by remember { mutableIntStateOf(3) }

        LaunchedEffect(key1 = count) {
            if (count > 0) {
                delay(1000)
                count--
            } else if (count == 0) {
                // Starts the timer animation after countdown
                onTimerRunningChange(true)
            }
        }
        Canvas(modifier = Modifier.size(200.dp), onDraw = {
            drawCircle(color = color)
        })
        AnimatedContent(targetState = count, label = "CountDown") { targetCount ->
            if (count > 0) {
                // Displays the countdown value
                Text(
                    text = "$targetCount",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

// Countdown Beep for when timer is about to finish
@Composable
fun CountdownBeepPlayer(
    timerUiState: TimerUiState,
    timerViewModel: TimerViewModel,
    isTimerRunning: Boolean
) {
    if (timerUiState.sound && isTimerRunning) {
        timerViewModel.player.play()
    }
}

// Handle vibration feedback
@Composable
fun Vibration(timerUiState: TimerUiState) {
    Column {
        val mContext = LocalContext.current

        if (timerUiState.vibrate) {
            LaunchedEffect(Unit) {
                TimerUtil.vibrate(mContext, timerUiState)
            }
        }
    }
}