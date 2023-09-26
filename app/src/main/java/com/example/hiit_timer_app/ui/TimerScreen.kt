package com.example.hiit_timer_app.ui

import android.app.Application
import android.content.Context
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hiit_timer_app.R
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.util.TimerUtil

// Composable that renders TimerScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    context: Context
) {

    //keep track of the timer running state
    var isTimerRunning by remember { mutableStateOf(false) }

    // Prevent screen from turn off
    KeepScreenOn()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Back to Setup",
                        style = MaterialTheme.typography.displayLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
//                            tint = Color.White
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
                .padding(innerPadding),
//            color = Color.Black
        ) {

            BackHandler {
                onBackPressed()
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                StateText(timerUiState)

                Animation(
                    timerUiState = timerUiState,
                    viewModel = viewModel,
                    isTimerRunning = isTimerRunning,
                    changeTimerRunning = { isTimerRunning = it },
                    context = context
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

// Handles with the text that shows if is active, rest timer or if its finished
@Composable
fun StateText(timerUiState: TimerUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = when (timerUiState.currentTimerType) {
                TimerType.ACTIVE -> {
                    stringResource(R.string.go)
                }

                TimerType.REST -> {
                    stringResource(R.string.rest)
                }

                else -> {
                    stringResource(R.string.finish)
                }
            },
            fontSize = 50.sp,
            style = MaterialTheme.typography.displayLarge,
//            color = Color.White
        )
    }
}

@Composable
fun RoundCounter(uiState: TimerUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = TimerUtil.displayRoundsOnTimerScreen(uiState.rounds, uiState.currentRound),
//            color = Color.White,
            style = MaterialTheme.typography.displayMedium,
            fontSize = 30.sp
        )
    }
}

// Composable that handle with the buttons
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
        IconButton(
            onClick = {
                changeTimerRunning(!isTimerRunning)
                viewModel.updateProgress(uiState.current / uiState.initial.toFloat())
                if (viewModel.player.isPlaying() && uiState.sound) {
                    viewModel.player.pause()
                } else if (!viewModel.player.isPlaying() && uiState.sound) {
                    viewModel.player.play()
                }
            },
            modifier = Modifier.size(dimensionResource(id = R.dimen.timer_screen_width_size_elements))
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
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(dimensionResource(id = R.dimen.timer_screen_width_size_elements))
        ) {
            Icon(
                imageVector = Icons.Outlined.Replay,
                contentDescription = "",
//                tint = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            )
        }
    }
}

// Prevent screen from turn off
@Composable
fun KeepScreenOn() {
    val currentView = LocalView.current
    DisposableEffect(Unit) {
        currentView.keepScreenOn = true
        onDispose {
            currentView.keepScreenOn = false
        }
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
    val timerUiState = TimerUiState(
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
    )

    val viewModel = TimerViewModel(Application())

    TimerScreen(
        onBackPressed = {},
        timerUiState = timerUiState,
        viewModel = viewModel,
        modifier = Modifier,
        context = LocalContext.current
    )
}