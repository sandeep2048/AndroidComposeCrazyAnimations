package com.xequal2.androidcomposecrazyanimations.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xequal2.androidcomposecrazyanimations.domainmorping.CloudOrbWithControls
import com.xequal2.androidcomposecrazyanimations.dotsandlines.DotsAndLinesComposeView
import com.xequal2.androidcomposecrazyanimations.particlesphere.ParticleSphere
import com.xequal2.androidcomposecrazyanimations.particlesphere.exploding.ExplodingSphereCanvas
import com.xequal2.androidcomposecrazyanimations.particlesphere.pull.TouchGravityCanvas
import com.xequal2.androidcomposecrazyanimations.particlesphere.shape.ShapeMorphingCanvas
import com.xequal2.androidcomposecrazyanimations.pongwars.PongWarsGame


@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.ScreenContent.route) {
        composable(Screen.ParticleSphere.route) { ParticleSphere() }
        composable(Screen.ParticleSphere2.route) { }
        composable(Screen.ParticleScreenContent.route) { ParticleScreenContent(navController) }
        composable(Screen.ExplodingSphereCanvas.route) { ExplodingSphereCanvas() }
        composable(Screen.TouchGravityCanvas.route) { TouchGravityCanvas() }
        composable(Screen.ShapeMorphingCanvas.route) { ShapeMorphingCanvas() }
        composable(Screen.PongWarsGame.route) { PongWarsGame() }
        composable(Screen.DotsAndLinesComposeView.route) { DotsAndLinesComposeView() }
        composable(Screen.CloudOrbWithControls.route) { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            CloudOrbWithControls()
        }
        }
        composable(Screen.ScreenContent.route) { ScreenContent(navController) }
    }
}





