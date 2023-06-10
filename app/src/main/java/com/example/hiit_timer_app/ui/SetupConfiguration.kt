package com.example.hiit_timer_app.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TimerConfiguration(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier
) {
    Column {
        SetTime(
            text = "Active",
            time = timerUiState.timeActive,
            viewModel = viewModel,
            changeTime = { time -> viewModel.updateTimeActive(time) },
            modifier = modifier
        )
        SetTime(
            text = "Rest",
            time = timerUiState.timeRest,
            viewModel = viewModel,
            changeTime = { time -> viewModel.updateTimeRest(time) },
            modifier = modifier
        )
        SetSwitch(
            soundState = timerUiState.sound,
            viewModel = TimerViewModel()
        )
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
fun SetTime(
    text: String,
    time: Long,
    viewModel: TimerViewModel,
    changeTime: (Long) -> Unit,
    modifier: Modifier,
) {
    var showDialogPicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 24.sp
        )
        TextButton(onClick = { showDialogPicker = true }) {
            Text(
                text = viewModel.formatTime(time),
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
        }
        if (showDialogPicker) {
            TimePickerDialog(
                onDismissRequest = { showDialogPicker = false },
                minutesInput = time / 60,
                secondsInput = time,
                onTimeUpdate = { updatedTime -> changeTime(updatedTime) }
            )
        }
    }
}


@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeUpdate: (Long) -> Unit,
    minutesInput: Long,
    secondsInput: Long,
) {
    var minutes by remember { mutableLongStateOf(minutesInput) }
    var seconds by remember { mutableLongStateOf(secondsInput) }

    Dialog(onDismissRequest = onDismissRequest) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Set Time") },
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumberPicker(
                            value = minutes,
                            onValueChange = { minutes = it },
                            minValue = 0,
                            maxValue = 2
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
                TextButton(onClick = {
                    val time = minutes * 60 + seconds
                    onTimeUpdate(time)
                    onDismissRequest()
                    Log.d("SaveButtonClicked", "Save Button was clicked!")
                }) {
                    Text(text = "Save")
                }
            }
        )
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
fun SetSwitch(
    modifier: Modifier = Modifier,
    soundState: Boolean,
    viewModel: TimerViewModel
) {
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
            checked = soundState,
            onCheckedChange = { checked ->
                viewModel.updateSoundEnabled(checked)
            }
        )
    }
}