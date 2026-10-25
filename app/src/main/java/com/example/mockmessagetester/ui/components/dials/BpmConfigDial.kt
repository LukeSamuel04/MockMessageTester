package com.example.mockmessagetester.ui.components.dials

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun BpmConfigDial(
    bpm: Int,
    onBpmChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isRunning: Boolean = false,
    // 【新增管线参数】：接收外部传入的震动开关指令
    isHapticEnabled: Boolean = true,
    onCenterClick: () -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current

    val minBpm = 1
    val maxBpm = 180
    val degreePerBpm = 6f
    val minAngle = minBpm * degreePerBpm
    val maxAngle = maxBpm * degreePerBpm

    var accumulatedAngle by remember { mutableFloatStateOf(bpm * degreePerBpm) }
    var lastTouchAngle by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(bpm) {
        val targetAngle = bpm * degreePerBpm
        if (abs(accumulatedAngle - targetAngle) > degreePerBpm) {
            accumulatedAngle = targetAngle
        }
    }

    val centerActiveColor = MaterialTheme.colorScheme.error
    val centerReadyColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceColor = MaterialTheme.colorScheme.onSurfaceVariant
    val matchaGreen = Color(0xFF81C784)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val centerX = size.width / 2f
                        val centerY = size.height / 2f
                        lastTouchAngle = calculateAngle(offset.x, offset.y, centerX, centerY)
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        val centerX = size.width / 2f
                        val centerY = size.height / 2f
                        val currentAngle = calculateAngle(change.position.x, change.position.y, centerX, centerY)

                        var delta = currentAngle - lastTouchAngle
                        if (delta > 180f) delta -= 360f
                        else if (delta < -180f) delta += 360f

                        lastTouchAngle = currentAngle
                        val oldBpm = (accumulatedAngle / degreePerBpm).roundToInt().coerceIn(minBpm, maxBpm)

                        val newAccumulated = (accumulatedAngle + delta).coerceIn(minAngle, maxAngle)
                        accumulatedAngle = newAccumulated

                        val newBpm = (newAccumulated / degreePerBpm).roundToInt().coerceIn(minBpm, maxBpm)

                        if (newBpm != oldBpm) {
                            // 【拦截点 1】：只有开关打开时，才在旋转刻度时提供震动反馈
                            if (isHapticEnabled) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                //android.util.Log.d("MockLabHaptic", "拨盘刻度震动被触发！")
                            }
                            onBpmChange(newBpm)
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val strokeWidthTrack = 16.dp.toPx()
            val strokeWidthHighlight = 10.dp.toPx()
            val gap = 12.dp.toPx()

            val outerHighlightRadius = size.minDimension / 2 - strokeWidthHighlight / 2 - 4.dp.toPx()
            val tickRadius = outerHighlightRadius - strokeWidthHighlight / 2 - gap - strokeWidthTrack / 2

            for (i in 0 until 60) {
                val tickAngle = (i * 6f) - 90f
                val rad = tickAngle * (PI / 180f)
                val innerRadius = tickRadius - strokeWidthTrack / 2
                val outerRadius = tickRadius + strokeWidthTrack / 2

                drawLine(
                    color = trackColor,
                    start = Offset(center.x + (innerRadius * cos(rad)).toFloat(), center.y + (innerRadius * sin(rad)).toFloat()),
                    end = Offset(center.x + (outerRadius * cos(rad)).toFloat(), center.y + (outerRadius * sin(rad)).toFloat()),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            val macroSweepAngle = bpm * 2f
            drawArc(
                color = matchaGreen,
                startAngle = -90f,
                sweepAngle = macroSweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidthHighlight, cap = StrokeCap.Round),
                size = Size(outerHighlightRadius * 2, outerHighlightRadius * 2),
                topLeft = Offset(center.x - outerHighlightRadius, center.y - outerHighlightRadius)
            )

            val knobAngle = (accumulatedAngle % 360f) - 90f
            val knobRad = knobAngle * (PI / 180f)
            val knobRadius = 10.dp.toPx()

            drawCircle(
                color = Color.White,
                radius = knobRadius,
                center = Offset(center.x + (tickRadius * cos(knobRad)).toFloat(), center.y + (tickRadius * sin(knobRad)).toFloat())
            )
            drawCircle(
                color = matchaGreen,
                radius = knobRadius - 2.dp.toPx(),
                center = Offset(center.x + (tickRadius * cos(knobRad)).toFloat(), center.y + (tickRadius * sin(knobRad)).toFloat())
            )
        }

        val elevation = if (isRunning) 0.dp else 6.dp
        Box(
            modifier = Modifier
                .fillMaxSize(0.55f)
                .shadow(elevation, CircleShape)
                .clip(CircleShape)
                .background(surfaceColor)
                .clickable {
                    // 【拦截点 2】：只有开关打开时，才在点击中心按钮时提供重震动反馈
                    if (isHapticEnabled) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        //android.util.Log.d("MockLabHaptic", "拨盘刻度震动被触发！")
                    }
                    onCenterClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = bpm.toString(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isRunning) centerActiveColor else onSurfaceColor
                )
                Text(
                    text = if (isRunning) "STOP" else "START (PBM)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isRunning) centerActiveColor else centerReadyColor,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

private fun calculateAngle(touchX: Float, touchY: Float, centerX: Float, centerY: Float): Float {
    val dx = touchX - centerX
    val dy = touchY - centerY
    var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat() + 90f
    if (angle > 180f) angle -= 360f
    return angle
}