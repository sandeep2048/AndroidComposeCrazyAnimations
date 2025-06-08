package com.xequal2.androidcomposecrazyanimations.particlesphere.pull

class GravityParticle(
    val original: Vector3,
    var current: Vector3 = original,
    var velocity: Vector3 = Vector3(0.0, 0.0, 0.0)
) {
    fun update(touchPoint: Vector3?) {
        val attraction = touchPoint?.let {
            val distance = (it - current)
            val factor = (1.0 / (distance.x * distance.x + distance.y * distance.y + 0.001)).coerceAtMost(0.03)
            distance.normalize() * factor
        } ?: Vector3(0.0, 0.0, 0.0)

        val homePull = (original - current) / 10.0
        velocity += homePull + attraction
        current += velocity
        velocity = velocity * 0.92
    }
}
