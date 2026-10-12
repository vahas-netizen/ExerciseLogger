package com.example.consciousworkouts.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = primaryGreen,
    onPrimary = onPrimaryGreen,
    primaryContainer = primaryGreenDark,
    onPrimaryContainer = Color.White,
    secondary = primaryGreenDark,
    onSecondary = Color.White,
    background = backgroundBlack,
    onBackground = Color(0xffF5F7F5),
    surface = consciousGrey,
    onSurface = Color(0xffF5F7F5),
    surfaceVariant = progressTrack,
    onSurfaceVariant = Color(0xffB7BDB8),
    outline = Color(0xff444844)
)

@Composable
fun ConsciousWorkoutsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}