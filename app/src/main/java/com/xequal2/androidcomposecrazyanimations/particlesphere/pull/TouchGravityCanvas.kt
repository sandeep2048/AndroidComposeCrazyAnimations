package com.xequal2.androidcomposecrazyanimations.particlesphere.pull


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.xequal2.androidcomposecrazyanimations.particlesphere.pull.GravityParticle
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun TouchGravityCanvas(modifier: Modifier = Modifier) {
    val particleCount = 1000
    val particles = remember {
        List(particleCount) {
            val origin = Vector3(
                x = Random.nextDouble(-1.0, 1.0),
                y = Random.nextDouble(-1.0, 1.0),
                z = Random.nextDouble(-1.0, 1.0)
            ).normalize() * Random.nextDouble(0.5, 1.0)
            GravityParticle(origin)
        }
    }

    var angleX by remember { mutableStateOf(0f) }
    var angleY by remember { mutableStateOf(0f) }
    var zoom by remember { mutableStateOf(1f) }

    var touchOffset by remember { mutableStateOf<Offset?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            angleX += 0.004f
            angleY += 0.007f
            val touch3D = touchOffset?.let {
                Vector3(
                    (it.x / 1000f) * 2 - 1.0,
                    -(it.y / 1000f) * 2 + 1.0,
                    0.0
                )
            }
            particles.forEach { it.update(touch3D) }
            delay(16)
        }
    }

    val gestureModifier = Modifier.pointerInput(Unit) {
        awaitEachGesture {
            do {
                val event = awaitPointerEvent()
                val pos = event.changes.first().position
                touchOffset = pos
            } while (event.changes.any { it.pressed })
            touchOffset = null
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

        particles.forEach { dot ->
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
