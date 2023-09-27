package com.example.hiit_timer_app.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hiit_timer_app.R
import com.example.hiit_timer_app.RunningService
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.util.TimerUtil

@Composable
fun TimerApp(
    uiState: TimerUiState,
    modifier: Modifier = Modifier,
    context: Context
) {

    val viewModel: TimerViewModel = viewModel()
    val timerUiState by viewModel.uiState.collectAsState()

    val navController = rememberNavController()

    val activity = (LocalContext.current as? Activity)

    NavHost(navController, startDestination = "home") {
        composable("home") {
            TimerHomeScreen(
                timerUiState = timerUiState,
                onStartPressed = {
                    Intent(context.applicationContext, RunningService::class.java).also {
                        it.action = RunningService.Actions.START.toString()
                        context.startService(it)
                    }
                    viewModel.setTimerToStart()
                    navController.navigate("timer screen")
                },
                viewModel = viewModel,
                onBackPressed = { activity?.finishAffinity() }
            )
        }
        composable("timer screen") {
            TimerScreen(
                onBackPressed = { navController.navigate("home") },
                timerUiState = timerUiState,
                viewModel = viewModel,
                context = context
            )
        }
    }
    if (timerUiState.currentTimerType == TimerType.FINISH) {
        Intent(context.applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.STOP.toString()
            context.startService(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerHomeScreen(
    timerUiState: TimerUiState,
    onStartPressed: () -> Unit,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    BackHandler {
        onBackPressed()
    }
    Scaffold(
        topBar = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 12.dp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "HIIT App",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TimeWorkout(timerUiState)
                TimerConfiguration(
                    timerUiState = timerUiState,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButton(
                    onClick = onStartPressed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = dimensionResource(id = R.dimen.padding_small),
                            start = 8.dp,
                            end = 8.dp
                        ),
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Text(
                        text = "Start HIIT",
                        style = MaterialTheme.typography.displayMedium,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun TimeWorkout(timerUiState: TimerUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(start = 12.dp, end = 8.dp, bottom = 12.dp)
    ) {
        Text(
            text = "WORKOUT LENGTH",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = TimerUtil.showWorkoutLength(timerUiState),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun TimerAppPreview(modifier: Modifier = Modifier) {
    TimerApp(
        uiState = TimerUiState(
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
        context = LocalContext.current
    )
}