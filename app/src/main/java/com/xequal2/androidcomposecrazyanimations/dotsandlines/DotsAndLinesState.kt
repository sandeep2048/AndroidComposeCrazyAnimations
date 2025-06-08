package com.xequal2.androidcomposecrazyanimations.dotsandlines

import androidx.compose.ui.unit.IntSize
import com.xequal2.androidcomposecrazyanimations.dotsandlines.Dot.Companion.next
import kotlin.math.roundToInt

data class DotsAndLinesState(
    val dots: List<Dot> = emptyList(),
    val pointer: Dot? = null,
    val dotRadius: Float,
    val size: IntSize = IntSize.Zero,
    val speed: Float
) {

    companion object {
        // TODO(): A real size changed algorithm instead of resetting everything
        fun DotsAndLinesState.sizeChanged(
            size: IntSize,
            populationFactor: Float
        ) : DotsAndLinesState {
            if (size == this.size) return this
            return copy(
                dots = (0..size.realPopulation(populationFactor)).map {
                    Dot.create(size)
                },
                size = size
            )
        }

        fun DotsAndLinesState.next(durationMillis: Long): DotsAndLinesState {
            return copy(
                dots = dots.map {
                    it.next(size, durationMillis, dotRadius, speed)
                }
            )
        }

        fun DotsAndLinesState.populationControl(populationFactor: Float): DotsAndLinesState {
            val count = size.realPopulation(populationFactor = populationFactor)

            return if(count < dots.size) {
                copy(dots = dots.shuffled().take(count))
            } else {
                copy(dots = dots + (0..count-dots.size).map { Dot.create(size) })
            }
        }

        private fun IntSize.realPopulation(populationFactor: Float): Int {
            return (width * height / 10_000 * populationFactor).roundToInt()
        }
    }
}