package com.example.hiit_timer_app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Animation() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.size(size = 325.dp),
            color = Color.Cyan,
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round,
        )
        // Render the formatted time based on the active or rest timer
        Text(
            text = "00:00",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,

            )
    }
}