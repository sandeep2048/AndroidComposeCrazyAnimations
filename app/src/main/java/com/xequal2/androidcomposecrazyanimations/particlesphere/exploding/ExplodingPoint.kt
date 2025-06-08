package com.xequal2.androidcomposecrazyanimations.particlesphere.exploding

import kotlin.random.Random

class ExplodingPoint(val origin: Vector3D) {
    var current = origin
    var velocity = Vector3D(0.0, 0.0, 0.0)

    fun explode() {
        velocity = origin.normalize() * Random.nextDouble(0.05, 0.1)
    }

    fun regroup() {
        velocity = (origin - current) / 10.0
    }

    fun update() {
        current += velocity
        velocity = velocity * 0.95
    }
}
