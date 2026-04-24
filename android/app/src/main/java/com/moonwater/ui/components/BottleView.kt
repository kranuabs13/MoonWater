package com.moonwater.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun BottleView(
    level: Float, // 0.0 to 1.0
    onLevelChange: (Float) -> Unit,
    onDragEnd: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var previewLevel by remember { mutableStateOf(level) }
    val animatedLevel by animateFloatAsState(targetValue = if (previewLevel != level) previewLevel else level, label = "water_level")

    Box(
        modifier = modifier
            .width(160.dp)
            .height(380.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val newLevel = 1f - (offset.y / size.height).coerceIn(0f, 1f)
                    previewLevel = newLevel
                    onLevelChange(newLevel)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val newLevel = (previewLevel - dragAmount.y / size.height).coerceIn(0f, 1f)
                        previewLevel = newLevel
                    },
                    onDragEnd = {
                        onDragEnd(previewLevel)
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 6.dp.toPx()
            val bottleColor = Color.DarkGray
            val waterColor = Color(0xFF2196F3)

            // Define Bottle Outline Path
            val path = Path().apply {
                val w = size.width
                val h = size.height
                val r = 40.dp.toPx()
                val neckW = 40.dp.toPx()
                val neckH = 40.dp.toPx()
                val shoulderH = 40.dp.toPx()

                // Start from bottom left
                moveTo(r, h)
                lineTo(w - r, h)
                arcTo(androidx.compose.ui.geometry.Rect(w - 2 * r, h - 2 * r, w, h), 90f, -90f, false)
                
                // Right side up to shoulder
                lineTo(w, r + neckH + shoulderH)
                
                // Curve to neck
                quadraticBezierTo(w, r + neckH, w / 2 + neckW / 2, r + neckH)
                
                // Neck up
                lineTo(w / 2 + neckW / 2, r)
                
                // Top (Cap would go here)
                lineTo(w / 2 - neckW / 2, r)
                
                // Neck left side down
                lineTo(w / 2 - neckW / 2, r + neckH)
                
                // Curve to shoulder
                quadraticBezierTo(0f, r + neckH, 0f, r + neckH + shoulderH)
                
                // Left side down
                lineTo(0f, h - r)
                arcTo(androidx.compose.ui.geometry.Rect(0f, h - 2 * r, 2 * r, h), 180f, -90f, false)
                close()
            }

            // Draw Bottle Border
            drawPath(
                path = path,
                color = bottleColor,
                style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
            )

            // Clip and Draw Water
            clipPath(path) {
                val waterHeight = size.height * animatedLevel
                drawRect(
                    color = waterColor.copy(alpha = 0.8f),
                    topLeft = Offset(0f, size.height - waterHeight),
                    size = Size(size.width, waterHeight)
                )
            }
            
            // Draw Cap
            drawRect(
                color = Color.Gray,
                topLeft = Offset(size.width / 2 - 25.dp.toPx(), 0f),
                size = Size(50.dp.toPx(), 20.dp.toPx())
            )
        }
    }
}
