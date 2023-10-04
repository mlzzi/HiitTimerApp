package com.example.hiit_timer_app.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.hiit_timer_app.ui.theme.Picker
import com.example.hiit_timer_app.ui.theme.rememberPickerState

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeUpdate: (Int) -> Unit,
    minutesInput: Int,
    secondsInput: Int,
) {
    val minutes by remember { mutableIntStateOf(minutesInput) }
    val seconds by remember { mutableIntStateOf(secondsInput) }

    Dialog(onDismissRequest = onDismissRequest) {
        AlertDialog(
            modifier = Modifier.height(300.dp),
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Set Time", style = MaterialTheme.typography.displayMedium) },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Minutes")
                        Spacer(modifier = Modifier.width(35.dp))
                        Text(text = "Seconds")
                    }

                    Spacer(modifier = Modifier.width(50.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(50.dp))

                        PickerApp(startIndex = 10)

                        Text(
                            text = ":",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )

                        PickerApp(startIndex = seconds)

                        Spacer(modifier = Modifier.width(50.dp))

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
                    Text(text = "Save", style = MaterialTheme.typography.displayMedium)
                }
            }
        )
    }
}

@Composable
fun PickerApp(
    startIndex: Int
) {
    Column() {

        val pickerItems = remember { (1..59).map { it.toString() } }
        val pickerState = rememberPickerState()
        Picker(
            state = pickerState,
            items = pickerItems,
            visibleItemsCount = 3,
            startIndex = startIndex,
            textModifier = Modifier.padding(8.dp),
            textStyle = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun RoundDialog(
    onDismissRequest: () -> Unit,
    onRoundUpdate: (Int) -> Unit,
    roundsInput: Int,
) {
    var rounds by remember { mutableIntStateOf(roundsInput) }
    Dialog(onDismissRequest = onDismissRequest) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Set Time") },
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumberPicker(
                            value = rounds.toInt(),
                            onValueChange = { rounds = it.toInt() },
                            minValue = 1,
                            maxValue = 10
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onRoundUpdate(rounds.toInt())
                    onDismissRequest()
                    Log.d("SaveButtonClicked", "Save Button was clicked!")
                }) {
                    Text(text = "Save")
                }
            }
        )
    }
}

//Picker Antigo
@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int,
    maxValue: Int
) {
    var number by remember { mutableIntStateOf(value) }

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
fun RestartTimer(
    onDismissRequest: () -> Unit,
    onRestart: () -> Unit,
    viewModel: TimerViewModel,
    timerUiState: TimerUiState
) {
    Dialog(onDismissRequest = onDismissRequest) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Restart Timer") },
            text = {
                Column {
                    Text(
                        text = "Do you wish to restart timer?",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismissRequest()
                    if (timerUiState.sound) viewModel.player.play()
                }) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onRestart()
                    onDismissRequest()
                }) {
                    Text(text = "Confirm")
                }
            }
        )
    }
}