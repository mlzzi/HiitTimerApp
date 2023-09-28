package com.example.hiit_timer_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.hiit_timer_app.R

val Monda = FontFamily(
    Font(R.font.monda_regular),
    Font(R.font.monda_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Monda,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Monda,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Monda,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Monda,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Monda,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    ),
)