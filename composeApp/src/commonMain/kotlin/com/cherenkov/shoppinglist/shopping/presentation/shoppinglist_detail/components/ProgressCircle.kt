package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TaskProgressCircle(
    total: Int,
    completed: Int,
    modifier: Modifier = Modifier,
    circleSize: Dp = 120.dp,
    strokeWidth: Dp = 12.dp,
    backgroundColor: Color = Color.Gray.copy(alpha = 0.2f),
    progressColor: Color = Color(0xFF4CAF50),
    animationDuration: Int = 1000
) {
    val targetProgress = if (total > 0) completed.toFloat() / total else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = modifier.size(circleSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(circleSize)) {
            val size = size.minDimension
            val stroke = strokeWidth.toPx()
            val halfSize = size / 2f
            val radius = halfSize - stroke

            drawCircle(
                color = backgroundColor,
                radius = radius,
                center = Offset(halfSize, halfSize),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            val sweepAngle = 360f * animatedProgress

            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(halfSize - radius, halfSize - radius),
                size = Size(radius * 2f, radius * 2f),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Text(
            text = "$completed/$total",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}