package com.xequal2.androidcomposecrazyanimations.particlesphere

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ParticleSphere(modifier: Modifier = Modifier) {
    val particleCount = 1000
    val particles = remember {
        List(particleCount) {
            Particle(
                x = Random.nextDouble(-1.0, 1.0),
                y = Random.nextDouble(-1.0, 1.0),
                z = Random.nextDouble(-1.0, 1.0)
            ).normalize() * Random.nextDouble(0.5, 1.0)
        }
    }

    val angle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            angle.snapTo(angle.value + 0.01f)
            delay(16) // ~60fps
        }
    }

    Canvas(modifier = modifier
        .fillMaxSize()
        .background(Color.Black)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val scale = size.minDimension / 3

        particles.forEach { particle ->
            val rotated = particle.rotateY(angle.value)
            val perspective = 1.0 / (2.0 - rotated.z) // z affects scale
            val x2D = centerX + rotated.x * scale * perspective
            val y2D = centerY + rotated.y * scale * perspective
            val alpha = ((1.5 - rotated.z) / 2.0).coerceIn(0.1, 1.0).toFloat()

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = 1.5f * perspective.toFloat(),
                center = Offset(x2D.toFloat(), y2D.toFloat())
            )
        }
    }
}

