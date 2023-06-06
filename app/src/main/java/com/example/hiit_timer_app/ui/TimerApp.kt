package com.example.hiit_timer_app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TimerApp(
    modifier: Modifier = Modifier
) {
    val viewModel: TimerViewModel = viewModel()
    val pipaUiState by viewModel.uiState.collectAsState()

    val navController = rememberNavController()
    
    NavHost(navController, startDestination = "home") {
        composable("home") {
            TimerHomeScreen {
                navController.navigate("timer screen")
            }       
        }
        composable("timer screen") {
            TimerScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerHomeScreen(
//    timerUiState: TimerUIState,
    onStartPressed: () -> Unit,
//    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                HomeScreenHeader()
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SetTimer()
            Button(
                onClick = onStartPressed
            ) {
                Text(text = "Start HIIT")
            }
        }
    }

}

@Composable
fun SetTimer() {
    Column() {
        Row() {
            Text(text = "Active")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "20 sec")
            }
        }
        Row() {
            Text(text = "Active")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "120 sec")
            }
        }
        Row() {
            Text(text = "Active")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "6")
            }
        }
        Row() {
            Text(text = "Active")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "20 sec")
            }

        }
    }
}

@Composable
fun HomeScreenHeader() {
    
}


@Composable
fun TimerScreen() {
    Text("screen 2")
}
