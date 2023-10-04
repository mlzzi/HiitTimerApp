package com.example.hiit_timer_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import com.example.hiit_timer_app.R

val Ubuntu = FontFamily(
    Font(R.font.ubuntu_bold_italic),
)

val Brandon = FontFamily(
    Font(R.font.brandon_regular),
    Font(R.font.brandon_medium, FontWeight.Medium),
    Font(R.font.brandon_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Brandon,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Bold,
        fontSize = 46.sp
    )
)