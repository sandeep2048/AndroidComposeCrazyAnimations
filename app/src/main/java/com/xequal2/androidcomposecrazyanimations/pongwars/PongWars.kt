package com.xequal2.androidcomposecrazyanimations.pongwars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random
@Composable
fun PongWarsGame() {
    val canvasSize = 600f
    val squareSize = 25f
    val numSquares = (canvasSize / squareSize).toInt()
    val dayColor = Color(0xFFD9E8E3)
    val nightColor = Color(0xFF172B36)
    val dayBallColor = nightColor
    val nightBallColor = dayColor
    val frameRate = 100L

    var dayScore by remember { mutableStateOf(0) }
    var nightScore by remember { mutableStateOf(0) }

    val squares = remember {
        List(numSquares) { i ->
            MutableList(numSquares) { j ->
                if (i < numSquares / 2) dayColor else nightColor
            }
        }
    }

    data class Ball(
        var x: Float,
        var y: Float,
        var dx: Float,
        var dy: Float,
        val reverseColor: Color,
        val ballColor: Color
    )

    val balls = remember {
        mutableStateListOf(
            Ball(canvasSize / 4, canvasSize / 2, 8f, -8f, dayColor, dayBallColor),
            Ball((canvasSize / 4) * 3, canvasSize / 2, -8f, 8f, nightColor, nightBallColor)
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            dayScore = 0
            nightScore = 0

            balls.forEach { ball ->
                val radius = squareSize / 2

                // Boundary collision
                if (ball.x + ball.dx > canvasSize - radius || ball.x + ball.dx < radius) ball.dx *= -1
                if (ball.y + ball.dy > canvasSize - radius || ball.y + ball.dy < radius) ball.dy *= -1

                // Square collision
                for (angle in 0 until 8) {
                    val theta = angle * (Math.PI / 4).toFloat()
                    val checkX = ball.x + cos(theta) * radius
                    val checkY = ball.y + sin(theta) * radius
                    val i = (checkX / squareSize).toInt()
                    val j = (checkY / squareSize).toInt()
                    if (i in 0 until numSquares && j in 0 until numSquares) {
                        if (squares[i][j] != ball.reverseColor) {
                            squares[i][j] = ball.reverseColor
                            if (abs(cos(theta)) > abs(sin(theta))) ball.dx *= -1 else ball.dy *= -1
                        }
                    }
                }

                // Ball movement
                ball.x += ball.dx
                ball.y += ball.dy

                // Add randomness
                ball.dx += Random.nextFloat() * 0.02f - 0.01f
                ball.dy += Random.nextFloat() * 0.02f - 0.01f
                ball.dx = ball.dx.coerceIn(-10f, 10f).let { if (abs(it) < 5f) 5f * it.sign else it }
                ball.dy = ball.dy.coerceIn(-10f, 10f).let { if (abs(it) < 5f) 5f * it.sign else it }
            }

            delay(1000L / frameRate)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Draw grid
        for (i in 0 until numSquares) {
            for (j in 0 until numSquares) {
                drawRect(
                    color = squares[i][j],
                    topLeft = Offset(i * squareSize, j * squareSize),
                    size = Size(squareSize, squareSize)
                )
                if (squares[i][j] == dayColor) dayScore++
                if (squares[i][j] == nightColor) nightScore++
            }
        }

        // Draw balls
        balls.forEach { ball ->
            drawCircle(
                color = ball.ballColor,
                radius = squareSize / 2,
                center = Offset(ball.x, ball.y)
            )
        }
    }
}


