package com.moonwater.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = WaterBlue,
    secondary = WaterLight,
    tertiary = WaterDark
)

private val LightColorScheme = lightColorScheme(
    primary = WaterBlue,
    secondary = WaterDark,
    tertiary = WaterLight,
    background = Color(0xFFF1F8E9)
)

@Composable
fun MoonWaterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
