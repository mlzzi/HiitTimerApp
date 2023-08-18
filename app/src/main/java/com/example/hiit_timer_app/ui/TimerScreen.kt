package com.example.hiit_timer_app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiit_timer_app.R
import com.example.hiit_timer_app.model.TimerType

// Composable that renders TimerScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    // keep track of the countdown state
    var countdown by remember { mutableStateOf(timerUiState.countdown) }
    //keep track of the timer running state
    var isTimerRunning by remember { mutableStateOf(!countdown) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Back to Setup",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )

        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding), color = Color.Black
        ) {

            BackHandler {
                onBackPressed()
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                StateText(timerUiState)

                Animation(
                    timerUiState = timerUiState,
                    viewModel = viewModel,
                    countdown = countdown,
                    isTimerRunning = isTimerRunning,
                    changeCountdown = { countdown = it },
                    changeTimerRunning = { isTimerRunning = it }
                )

                RoundCounter(uiState = timerUiState)

                Buttons(
                    uiState = timerUiState,
                    viewModel = viewModel,
                    isTimerRunning = isTimerRunning,
                    changeTimerRunning = { isTimerRunning = it },
                )
            }
        }
    }
}

@Composable
fun StateText(timerUiState: TimerUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (timerUiState.currentTimerType == TimerType.ACTIVE) {
                stringResource(R.string.go)
            } else if (timerUiState.currentTimerType == TimerType.REST) {
                stringResource(R.string.rest)
            } else {
                stringResource(R.string.finish)
            },
            fontSize = 50.sp,
            color = Color.White
        )
    }
}

@Composable
fun RoundCounter(uiState: TimerUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Round ${uiState.rounds}/6",
            color = Color.White,
            fontSize = 30.sp
        )
    }
}

@Composable
fun Buttons(
    uiState: TimerUiState,
    viewModel: TimerViewModel,
    isTimerRunning: Boolean,
    changeTimerRunning: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(80.dp)) {
            Icon(
                imageVector = Icons.Outlined.Replay,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            )
        }
        IconButton(
            onClick = {
                changeTimerRunning(!isTimerRunning)
                viewModel.updateProgress(uiState.current / uiState.initial.toFloat())
//                progress = totalTime / initialValue.toFloat()
//                viewModel.updateProgress(TimerUtil.calculateSpinProgress(totalTime, initialTime))
//                changeProgress(TimerUtil.calculateSpinProgress(totalTime, initialTime))
            },
            modifier = Modifier.size(120.dp)
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
                modifier = Modifier.fillMaxSize(1f)
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(80.dp)) {
            Icon(
                imageVector = Icons.Outlined.StopCircle,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            )
        }
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
    val timerUiState = TimerUiState(
        timeActive = 5L,
        timeRest = 10L,
        progress = 1f,
        rounds = 6,
        sound = true,
        vibrate = false,
        countdown = false,
        current = 5L,
        initial = 5L,
    )
    val viewModel = TimerViewModel()

    TimerScreen(
        onBackPressed = {},
        timerUiState = timerUiState,
        viewModel = viewModel,
        modifier = Modifier
    )
}