package com.xequal2.androidcomposecrazyanimations.particlesphere.shape

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import kotlin.math.*
import kotlin.random.Random

class MorphingDot(
    val original: Vec3,
    var current: Vec3 = original,
    var target: Vec3 = original
) {
    var velocity = Vec3(0.0, 0.0, 0.0)

    fun moveTo(newTarget: Vec3) {
        target = newTarget
    }

    fun resetToSphere() {
        target = original
    }

    fun update() {
        velocity = (target - current) / 10.0
        current += velocity
        velocity *= 0.9
    }
}

fun generateHeartShape(count: Int): List<Vec3> {
    return List(count) {
        val t = Random.nextDouble(0.0, 2 * Math.PI)
        val x = 16 * sin(t).pow(3) / 20
        val y = (13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)) / 20
        Vec3(x, y, 0.0)
    }
}

fun generateHindiPeyShape(
    text: String = "पे",
    width: Int = 200,
    height: Int = 200,
    maxPoints: Int = 1000
): List<Vec3> {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.BLACK)

    val paint = Paint().apply {
        color = Color.WHITE
        textSize = height * 0.8f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }

    val xPos = width / 2f
    val yPos = (height / 2f - (paint.descent() + paint.ascent()) / 2)

    canvas.drawText(text, xPos, yPos, paint)

    val points = mutableListOf<Vec3>()
    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixel = bitmap.getPixel(x, y)
            val r = Color.red(pixel)
            val g = Color.green(pixel)
            val b = Color.blue(pixel)

            if (r > 200 && g > 200 && b > 200) {
                val normX = (x.toDouble() / width) * 2 - 1
                val normY = -((y.toDouble() / height) * 2 - 1)
                points.add(Vec3(normX, normY, 0.0))
            }
        }
    }

    return points.shuffled().take(maxPoints)
}

fun generatePhonePeShape(bitmap: Bitmap, maxPoints: Int = 1000): List<Vec3> {
    val logoPoints = mutableListOf<Vec3>()
    val width = bitmap.width
    val height = bitmap.height

    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixel = bitmap.getPixel(x, y)
            val r = Color.red(pixel)
            val g = Color.green(pixel)
            val b = Color.blue(pixel)

            // Threshold to detect white logo
            if (r > 200 && g > 200 && b > 200) {
                // Normalize x, y to range [-1, 1]
                val normX = (x.toDouble() / width) * 2 - 1
                val normY = -((y.toDouble() / height) * 2 - 1) // flip Y axis
                logoPoints.add(Vec3(normX, normY, 0.0))
            }
        }
    }

    return logoPoints.shuffled().take(maxPoints)
}
