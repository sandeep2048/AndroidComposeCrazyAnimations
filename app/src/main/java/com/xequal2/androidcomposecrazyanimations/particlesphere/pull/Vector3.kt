package com.xequal2.androidcomposecrazyanimations.particlesphere.pull
import kotlin.math.*

data class Vector3(var x: Double, var y: Double, var z: Double) {
    fun normalize(): Vector3 {
        val len = sqrt(x * x + y * y + z * z)
        return if (len == 0.0) this else Vector3(x / len, y / len, z / len)
    }

    operator fun plus(v: Vector3) = Vector3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)
    operator fun times(s: Double) = Vector3(x * s, y * s, z * s)
    operator fun div(s: Double) = Vector3(x / s, y / s, z / s)
    operator fun timesAssign(s: Double) { x *= s; y *= s; z *= s }

    fun rotatedXY(ax: Float, ay: Float): Vector3 {
        val cosX = cos(ax); val sinX = sin(ax)
        val cosY = cos(ay); val sinY = sin(ay)

        var ny = y * cosX - z * sinX
        var nz = y * sinX + z * cosX
        var nx = x * cosY + nz * sinY
        nz = -x * sinY + nz * cosY
        return Vector3(nx, ny, nz)
    }
}
