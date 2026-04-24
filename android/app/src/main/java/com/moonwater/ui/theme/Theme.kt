package com.moonwater.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    secondary = Color(0xFF81D4FA),
    tertiary = Color(0xFF80DEEA)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3),
    secondary = Color(0xFF03A9F4),
    tertiary = Color(0xFF00BCD4),
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
