package com.example.hiit_timer_app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TimerApp(modifier: Modifier = Modifier) {

    val viewModel: TimerViewModel = viewModel()
    val timerUiState by viewModel.uiState.collectAsState()

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            TimerHomeScreen(
                timerUiState = timerUiState,
                onStartPressed = { navController.navigate("timer screen") },
                viewModel = viewModel
            )
        }
        composable("timer screen") {
            TimerScreen()
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
                title = { HomeScreenHeader() },
                colors = topAppBarColors(
                containerColor = Yellow
                )
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding().navigationBarsPadding()
        ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SetTimer(
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

@Composable
fun SetTimer(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier
) {
    var showActiveDialog by remember { mutableStateOf(false) }
    var showRestDialog by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Active",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
            TextButton(onClick = { showActiveDialog = true }) {
                Text(
                    text = viewModel.formatTime(timerUiState.timeActive),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 24.sp
                )
            }
            TimePickerDialog(
                showDialog = showActiveDialog,
                onDismiss = { showActiveDialog = false },
                onSave = { time -> timerUiState.timeActive = time },
                minutesInput = timerUiState.timeActive / 60,
                secondsInput = timerUiState.timeActive % 60
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Rest",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
            TextButton(onClick = { showRestDialog = true }) {
                Text(
                    text = viewModel.formatTime(timerUiState.timeRest),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 24.sp
                )
            }
            TimePickerDialog(
                showDialog = showRestDialog,
                onDismiss = { showRestDialog = false },
                onSave = { time -> timerUiState.timeRest = time },
                minutesInput = timerUiState.timeRest / 60,
                secondsInput = timerUiState.timeRest % 60
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sound",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
            Switch(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .semantics { contentDescription = "Demo" },
                checked = timerUiState.sound,
                onCheckedChange = { checked ->
                    viewModel.updateSoundEnabled(checked)
                }
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Vibrate",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
            Switch(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .semantics { contentDescription = "Demo" },
                checked = timerUiState.vibrate,
                onCheckedChange = { checked ->
                    viewModel.updateVibrateEnabled(checked)
                }
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Countdown",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
            Switch(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .semantics { contentDescription = "Demo" },
                checked = timerUiState.countDown,
                onCheckedChange = { checked ->
                    viewModel.updateCountdownEnabled(checked)
                }
            )
        }
    }
}

@Composable
fun HomeScreenHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = "Hi!")
    }
}

@Composable
fun TimePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSave: (Long) -> Unit,
    minutesInput: Long,
    secondsInput: Long
) {
    if (showDialog) {
        var minutes = minutesInput
        var seconds = secondsInput

        Dialog(onDismissRequest = onDismiss) {
            AlertDialog(
                onDismissRequest = onDismiss,
                title = { Text(text = "Set Time") },
                text = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NumberPicker(
                                value = minutes,
                                onValueChange = { minutes = it * 60},
                                minValue = 0,
                                maxValue = 59
                            )
                            Text(text = ":", modifier = Modifier.padding(horizontal = 8.dp))
                            NumberPicker(
                                value = seconds,
                                onValueChange = { seconds = it },
                                minValue = 0,
                                maxValue = 59
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val time = minutes + seconds
                        onSave(time)
                        onDismiss()
                    }) {
                        Text(text = "Save")
                    }
                }
            )
        }
    }
}

@Composable
fun NumberPicker(
    value: Long,
    onValueChange: (Long) -> Unit,
    minValue: Int,
    maxValue: Int
) {
    var number by remember { mutableLongStateOf(value) }

    Column {
        Button(onClick = { if (number > minValue) number -= 1 }) {
            Text(text = "-", textAlign = TextAlign.Center)
        }

        Text(
            text = number.toString().padStart(2, '0'),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Button(onClick = { if (number < maxValue) number += 1 }) {
            Text(text = "+", textAlign = TextAlign.Center)
        }
    }

    LaunchedEffect(number) {
        onValueChange(number)
    }
}

@Composable
fun TimerScreen() {
    Text("screen 2")
}