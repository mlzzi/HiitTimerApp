package com.example.hiit_timer_app.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.hiit_timer_app.util.TimerUtil

@Composable
fun TimerConfiguration(
    timerUiState: TimerUiState,
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SetTime(
                text = "Active",
                time = timerUiState.timeActive,
                changeTime = { time -> viewModel.updateTimeActive(time) },
                modifier = modifier.semantics { contentDescription = "Active Button" },
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            SetTime(
                text = "Rest",
                time = timerUiState.timeRest,
                changeTime = { time -> viewModel.updateTimeRest(time) },
                modifier = modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer, thickness = 1.dp)
            SetRounds(
                text = "Rounds",
                changeRound = { round -> viewModel.updateRounds(round) },
                rounds = timerUiState.rounds,
                modifier = modifier
            )
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer, thickness = 1.dp)
            SetSwitch(
                text = "Sound",
                soundState = timerUiState.sound,
                onChangeSwitch = { switch -> viewModel.updateSoundEnabled(switch) }
            )
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer, thickness = 1.dp)
            SetSwitch(
                text = "Vibrate",
                soundState = timerUiState.vibrate,
                onChangeSwitch = { switch -> viewModel.updateVibrateEnabled(switch) }
            )
        }
    }
}

@Composable
fun SetTime(
    text: String,
    time: Int,
    changeTime: (Int) -> Unit,
    modifier: Modifier,
) {
    var showDialogPicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        TextButton(onClick = { showDialogPicker = true }) {
            Text(
                text = TimerUtil.formatTime(time),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.primary
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
fun SetRounds(
    text: String,
    rounds: Int,
    changeRound: (Int) -> Unit,
    modifier: Modifier,
) {
    var showDialogPicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        TextButton(onClick = { showDialogPicker = true }) {
            Text(
                text = rounds.toString(),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.primary
            )
        }
        if (showDialogPicker) {
            RoundDialog(
                onDismissRequest = { showDialogPicker = false },
                roundsInput = rounds,
                onRoundUpdate = { updatedTime -> changeRound(updatedTime) }
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
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Switch(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .semantics { contentDescription = "Demo" }
                .scale(0.8f),
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.inversePrimary,
                uncheckedBorderColor = MaterialTheme.colorScheme.primaryContainer,

            ),
            checked = soundState,
            onCheckedChange = { checked ->
                onChangeSwitch(checked)
            }
        )
    }
}