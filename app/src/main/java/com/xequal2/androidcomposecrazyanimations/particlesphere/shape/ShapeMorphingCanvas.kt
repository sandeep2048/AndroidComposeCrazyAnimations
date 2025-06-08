package com.xequal2.androidcomposecrazyanimations.particlesphere.shape

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ShapeMorphingCanvas(
    modifier: Modifier = Modifier,
) {
    val dotCount = 1000
    val dots = remember {
        List(dotCount) {
            val origin = Vec3(
                x = Random.nextDouble(-1.0, 1.0),
                y = Random.nextDouble(-1.0, 1.0),
                z = Random.nextDouble(-1.0, 1.0)
            ).normalize() * Random.nextDouble(0.5, 1.0)
            MorphingDot(origin)
        }
    }

    var angleX by remember { mutableStateOf(0f) }
    var angleY by remember { mutableStateOf(0f) }
    var zoom by remember { mutableStateOf(1f) }
    val scope = rememberCoroutineScope()

    // State: are we showing sphere or PhonePe shape?
    var isSphere by remember { mutableStateOf(true) }
    // Morph animation job to cancel previous if needed
    var morphJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            angleX += 0.004f
            angleY += 0.007f
            dots.forEach { it.update() }
            delay(16)
        }
    }

    val gestureModifier = Modifier.pointerInput(Unit) {
        detectTapGestures {
            morphJob?.cancel()
            morphJob = scope.launch {
                if (isSphere) {
                    // Morph to PhonePe logo
                    val phonePePoints = generateHindiPeyShape()
                    dots.zip(phonePePoints).forEach { (dot, target) ->
                        dot.moveTo(target)
                    }
                    isSphere = false
                } else {
                    // Morph back to sphere
                    dots.forEach { it.resetToSphere() }
                    isSphere = true
                }
            }
        }
        detectTransformGestures { _, _, zoomChange, _ ->
            zoom = (zoom * zoomChange).coerceIn(0.3f, 3f)
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .then(gestureModifier)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val baseScale = size.minDimension / 3 * zoom
        val pulse = ((sin(System.currentTimeMillis() / 300.0) + 1.0) * 0.5).toFloat()

        dots.forEach { dot ->
            val rotated = dot.current.rotatedXY(angleX, angleY)
            val perspective = 1.0 / (2.0 - rotated.z)
            val x2D = centerX + rotated.x * baseScale * perspective
            val y2D = centerY + rotated.y * baseScale * perspective
            val alpha = ((1.5 - rotated.z) / 2.0).coerceIn(0.1, 1.0).toFloat()
            val glow = (alpha * (0.8f + 0.2f * pulse)).coerceIn(0f, 1f)

            drawCircle(
                color = Color.White.copy(alpha = glow),
                radius = 1.5f * perspective.toFloat(),
                center = Offset(x2D.toFloat(), y2D.toFloat())
            )
        }
    }
}


