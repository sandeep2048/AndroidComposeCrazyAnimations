package com.xequal2.androidcomposecrazyanimations.particlesphere.exploding

import kotlin.math.*

data class Vector3D(var x: Double, var y: Double, var z: Double) {
    fun normalize(): Vector3D {
        val length = sqrt(x * x + y * y + z * z)
        return Vector3D(x / length, y / length, z / length)
    }

    fun rotatedXY(angleX: Float, angleY: Float): Vector3D {
        val cosX = cos(angleX)
        val sinX = sin(angleX)
        val cosY = cos(angleY)
        val sinY = sin(angleY)

        var newY = y * cosX - z * sinX
        var newZ = y * sinX + z * cosX
        var newX = x * cosY + newZ * sinY
        newZ = -x * sinY + newZ * cosY

        return Vector3D(newX, newY, newZ)
    }

    operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
    operator fun times(scalar: Double) = Vector3D(x * scalar, y * scalar, z * scalar)
    operator fun div(scalar: Double) = Vector3D(x / scalar, y / scalar, z / scalar)
    operator fun timesAssign(scalar: Double) {
        x *= scalar
        y *= scalar
        z *= scalar
    }
}
