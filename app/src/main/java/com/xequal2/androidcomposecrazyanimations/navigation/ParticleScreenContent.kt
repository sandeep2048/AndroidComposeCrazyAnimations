package com.xequal2.androidcomposecrazyanimations.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ParticleScreenContent(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Particle Navigation", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navHostController.navigate(Screen.ParticleSphere.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("ParticleSphere")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navHostController.navigate(Screen.ExplodingSphereCanvas.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Touch Explode")
        }
        Button(
            onClick = { navHostController.navigate(Screen.TouchGravityCanvas.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Touch and Drag")
        }

        Button(
            onClick = { navHostController.navigate(Screen.ShapeMorphingCanvas.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Phonepe Symbol")
        }
        Button(
            onClick = { navHostController.navigate(Screen.ShapeMorphingCanvas.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Image")
        }

        Button(
            onClick = { navHostController.navigate(Screen.ShapeMorphingCanvas.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Custom Text and Size")
        }
    }
}