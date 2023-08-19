package com.example.hiit_timer_app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hiit_timer_app.util.TimerUtil

@Composable
fun TimerApp(
    uiState: TimerUiState,
    modifier: Modifier = Modifier
) {

    val viewModel: TimerViewModel = viewModel()
    val timerUiState by viewModel.uiState.collectAsState()

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            TimerHomeScreen(
                timerUiState = timerUiState,
                onStartPressed = {
                    navController.navigate("timer screen")
                    viewModel.handleActiveTimerFinished(timerUiState.timeActive)
//                    viewModel.updateRounds(timerUiState.rounds)
                },
                viewModel = viewModel
            )
        }
        composable("timer screen") {
            TimerScreen(
                onBackPressed = { navController.navigate("home") },
                timerUiState = timerUiState,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerHomeScreen(
    timerUiState: TimerUiState,
    onStartPressed: () -> Unit,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "HIIT App",
                        color = Color.White
                    )
                },
                colors = topAppBarColors(
                    containerColor = Black
                )
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { contentPadding ->
        Surface(
            color = Black
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TimeWorkout(timerUiState)
                TimerConfiguration(
                    timerUiState = timerUiState,
                    viewModel = viewModel
                )
                Button(
                    onClick = onStartPressed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Start HIIT",
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TimeWorkout(timerUiState: TimerUiState) {
    Column {
        Text(
            text = "WORKOUT LENGTH",
            color = Color.White,
            fontSize = 30.sp
        )
        Text(
            text = TimerUtil.showWorkoutLength(timerUiState),
            color = Color.White,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun TimerAppPreview(modifier: Modifier = Modifier) {
    TimerApp(
        uiState = TimerUiState(
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