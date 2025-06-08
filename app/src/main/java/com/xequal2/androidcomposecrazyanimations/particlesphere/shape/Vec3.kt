package com.xequal2.androidcomposecrazyanimations.particlesphere.shape

import kotlin.math.*

data class Vec3(var x: Double, var y: Double, var z: Double) {
    fun normalize(): Vec3 {
        val len = sqrt(x * x + y * y + z * z)
        return Vec3(x / len, y / len, z / len)
    }

    fun rotatedXY(ax: Float, ay: Float): Vec3 {
        val cosX = cos(ax)
        val sinX = sin(ax)
        val cosY = cos(ay)
        val sinY = sin(ay)

        var y1 = y * cosX - z * sinX
        var z1 = y * sinX + z * cosX
        var x1 = x * cosY + z1 * sinY
        z1 = -x * sinY + z1 * cosY

        return Vec3(x1, y1, z1)
    }

    operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
    operator fun times(s: Double) = Vec3(x * s, y * s, z * s)
    operator fun div(s: Double) = Vec3(x / s, y / s, z / s)
}
