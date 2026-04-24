package com.moonwater.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun BottleView(
    level: Float, // 0.0 to 1.0
    onLevelChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedLevel by animateFloatAsState(targetValue = level, label = "water_level")

    Box(
        modifier = modifier
            .width(180.dp)
            .height(350.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val newLevel = 1f - (offset.y / size.height).coerceIn(0f, 1f)
                    onLevelChange(newLevel)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val bottleColor = Color.DarkGray
            val waterColor = Color(0xFF2196F3)

            // Draw Bottle Border
            drawRoundRect(
                color = bottleColor,
                size = size,
                cornerRadius = CornerRadius(40.dp.toPx(), 40.dp.toPx()),
                style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
            )

            // Draw Water
            val waterHeight = size.height * animatedLevel
            clipRect(
                top = size.height - waterHeight,
                bottom = size.height
            ) {
                drawRoundRect(
                    color = waterColor,
                    size = size,
                    cornerRadius = CornerRadius(40.dp.toPx(), 40.dp.toPx())
                )
            }
        }
    }
}
