package com.example.hiit_timer_app.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import kotlinx.coroutines.async
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
        Box(
            contentAlignment = Alignment.Center
        ) {
            SpinAnimation(
                timerUiState = timerUiState,
                totalTime = timerUiState.timeActive,
                onTimerChange = { time -> viewModel.updateTimeActive(time) },
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@Composable
fun SpinAnimation(
    timerUiState: TimerUiState,
    onTimerChange: (Long) -> Unit,
    totalTime: Long,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableLongStateOf(totalTime) }
    var progress by remember { mutableFloatStateOf(1f) }
    var isTimerRunning by remember { mutableStateOf(false) }

    // animation
    val progressAnimate by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    CircularProgressIndicator(
        progress = progressAnimate,
        modifier = Modifier.size(size = 300.dp),
        color = Color.Magenta,
        strokeWidth = 10.dp,
        strokeCap = StrokeCap.Round,
    )



    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            val progressJob = async {
                while (progress > 0f) {
                    delay(50L)
                    progress -= (180 / totalTime) / 3350.toFloat()
                }
            }

            val timerJob = async {
                while (currentTime > 0L) {
                    delay(1000L)
                    currentTime--
                    onTimerChange(currentTime)
                }
            }

            progressJob.await()
            timerJob.await()
        }
    }

    Text(
        text = TimerUtil.formatTime(timerUiState.timeActive),
        fontSize = 44.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    // add space between indicator and button
    Spacer(modifier = Modifier.height(height = 16.dp))

    // button
    Button(
        onClick = {
            isTimerRunning = !isTimerRunning
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

//    Button(
//        onClick = {
//            if (currentTime <= 0L) {
//                currentTime = totalTime
//                isTimerRunning = true
//            } else {
//                isTimerRunning = !isTimerRunning
//            }
//        },
//        modifier = Modifier.padding(top = 400.dp),
//    ) {
//        Text(
//            text = if (isTimerRunning && currentTime >= 0L) "Stop"
//            else if (!isTimerRunning && currentTime >= 0L) "Start"
//            else "Restart"
//        )
//    }