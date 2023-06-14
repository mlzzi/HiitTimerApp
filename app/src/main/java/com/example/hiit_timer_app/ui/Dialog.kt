package com.example.hiit_timer_app.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

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