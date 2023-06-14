package com.example.hiit_timer_app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import com.example.hiit_timer_app.util.TimerUtil

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
            text = "Sound",
            soundState = timerUiState.sound,
            onChangeSwitch = { switch -> viewModel.updateSoundEnabled(switch) }
        )
        SetSwitch(
            text = "Vibrate",
            soundState = timerUiState.vibrate,
            onChangeSwitch = { switch -> viewModel.updateVibrateEnabled(switch) }
        )
        SetSwitch(
            text = "Countdown",
            soundState = timerUiState.countDown,
            onChangeSwitch = { switch -> viewModel.updateCountdownEnabled(switch) }
        )
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
                text = TimerUtil.formatTime(time),
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp
            )
        }
        if (showDialogPicker) {
            TimePickerDialog(
                onDismissRequest = { showDialogPicker = false },
                minutesInput = time / 60,
                secondsInput = time % 60,
                onTimeUpdate = { updatedTime -> changeTime(updatedTime) }
            )
        }
    }
}

@Composable
fun SetSwitch(
    modifier: Modifier = Modifier,
    soundState: Boolean,
    onChangeSwitch: (Boolean) -> Unit,
    text: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 24.sp
        )
        Switch(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .semantics { contentDescription = "Demo" },
            checked = soundState,
            onCheckedChange = { checked ->
                onChangeSwitch(checked)
            }
        )
    }
}