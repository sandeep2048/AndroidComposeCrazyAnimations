package com.xequal2.androidcomposecrazyanimations.dotsandlines


import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize

@Composable
fun DotsAndLinesComposeView() {
    var dotsAndLinesConfig by rememberSaveable { mutableStateOf(DotsAndLinesConfig()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .dotsAndLines(
                contentColor = MaterialTheme.colorScheme.onSurface,
                threshold = dotsAndLinesConfig.threshold,
                maxThickness = dotsAndLinesConfig.maxThickness,
                dotRadius = dotsAndLinesConfig.dotRadius,
                speed = dotsAndLinesConfig.speedCoefficient,
                populationFactor = dotsAndLinesConfig.population
            )
    ) {

    }

}

@Parcelize
data class DotsAndLinesConfig(
    val threshold: Float = 0.07f,
    val maxThickness: Float = 5f,
    val dotRadius: Float = 4f,
    val speedCoefficient: Float = 0.05f,
    val population: Float = 0.1f // per 100^2 pixels
) : Parcelable


