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
fun ScreenContent(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Animations", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navHostController.navigate(Screen.ParticleScreenContent.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Particle Animation ")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navHostController.navigate(Screen.PongWarsGame.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("PongWarsGame")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navHostController.navigate(Screen.ExplodingSphereCanvas.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Todo")
        }
    }
}