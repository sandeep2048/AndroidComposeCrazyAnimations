package com.xequal2.androidcomposecrazyanimations.particlesphere

import kotlin.math.*
import kotlin.random.Random

data class Particle(var x: Double, var y: Double, var z: Double) {
    fun normalize(): Particle {
        val length = sqrt(x * x + y * y + z * z)
        return Particle(x / length, y / length, z / length)
    }

    operator fun times(scale: Double) = Particle(x * scale, y * scale, z * scale)

    fun rotateY(angle: Float): Particle {
        val cosA = cos(angle)
        val sinA = sin(angle)
        val newX = x * cosA + z * sinA
        val newZ = -x * sinA + z * cosA
        return Particle(newX, y, newZ)
    }
}
