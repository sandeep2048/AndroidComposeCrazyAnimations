package com.xequal2.androidcomposecrazyanimations.dotsandlines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import kotlin.math.sqrt

data class Dot(
    val position: Offset,
    val vector: Offset
) {
    companion object {
        /**
         * Calculate this [Dot]'s distance to another one.
         */
        infix fun Dot.distanceTo(another: Dot): Float {
            return (position - another.position).getDistance()
        }

        /**
         * Calculate where this dot will be in the next iteration.
         *
         * @param borders Size of the canvas where dots bounce.
         * @param durationMillis How long time is going to pass until next iteration.
         * @param dotRadius The radius of this dot when it is drawn.
         * @param speedCoefficient Although there is vector that indicates motion, this
         * parameter is used to speed up or down the animation at will.
         */
        fun Dot.next(
            borders: IntSize,
            durationMillis: Long,
            dotRadius: Float,
            speedCoefficient: Float
        ): Dot {
            val speed = vector * speedCoefficient

            return Dot(
                position = position + Offset(
                    x = speed.x / 1000f * durationMillis,
                    y = speed.y / 1000f * durationMillis,
                ),
                vector = vector
            ).let { (position, vector) ->
                val borderTop = dotRadius
                val borderLeft = dotRadius
                val borderBottom = borders.height - dotRadius
                val borderRight = borders.width - dotRadius
                Dot(
                    position = Offset(
                        x = when {
                            position.x < borderLeft -> borderLeft - (position.x - borderLeft)
                            position.x > borderRight -> borderRight - (position.x - borderRight)
                            else -> position.x
                        },
                        y = when {
                            position.y < borderTop -> borderTop - (position.y - borderTop)
                            position.y > borderBottom -> borderBottom - (position.y - borderBottom)
                            else -> position.y
                        }
                    ),
                    vector = Offset(
                        x = when {
                            position.x < borderLeft -> -vector.x
                            position.x > borderRight -> -vector.x
                            else -> vector.x
                        },
                        y = when {
                            position.y < borderTop -> -vector.y
                            position.y > borderBottom -> -vector.y
                            else -> vector.y
                        }
                    )
                )
            }
        }

        /**
         * Create a random dot instance belonging to @param borders.
         */
        fun create(borders: IntSize): Dot {
            return Dot(
                position = Offset(
                    (0..borders.width).random().toFloat(),
                    (0..borders.height).random().toFloat()
                ),
                vector = Offset(
                    // First, randomize direction. Second, randomize amplitude of speed vector.
                    listOf(-1f, 1f).random() * ((borders.width.toFloat() / 100f).toInt()..(borders.width.toFloat() / 10f).toInt()).random()
                        .toFloat(),
                    listOf(-1f, 1f).random() * ((borders.height.toFloat() / 100f).toInt()..(borders.height.toFloat() / 10f).toInt()).random()
                        .toFloat()
                )
            )
        }

        // Treat offset as a vector
        fun Offset.normalize(): Offset {
            val l = 1.0f / length()
            return Offset(x * l, y * l)
        }

        fun Offset.length(): Float {
            return sqrt(x * x + y * y)
        }
    }
}