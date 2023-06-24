package com.example.hiit_timer_app.ui

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
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable that renders TimerScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Back to Setup",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
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
                .padding(innerPadding), color = Color.DarkGray
        ) {
            BackHandler() {
                onBackPressed()
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                StateText()

                Animation()

                RoundCounter()

                Buttons()
            }
        }
    }
}

@Composable
fun StateText() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "GO!", fontSize = 50.sp, color = Color.White)
    }
}

@Composable
fun RoundCounter() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Round 1/6", color = Color.White, fontSize = 30.sp)
    }
}

@Composable
fun Buttons() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(80.dp)) {
            Icon(
                imageVector = Icons.Outlined.Replay,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(120.dp)) {
            Icon(
                imageVector = Icons.Outlined.PlayCircle,
                contentDescription = "",
                tint = Color.Magenta,
                modifier = Modifier.fillMaxSize(1f)
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(80.dp)) {
            Icon(
                imageVector = Icons.Outlined.StopCircle,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            )
        }
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
//    val timerUiState = TimerUiState() // Initialize your TimerUiState object here
//    val viewModel = TimerViewModel() // Initialize your TimerViewModel object here

    TimerScreen(onBackPressed = {}, modifier = Modifier)
}