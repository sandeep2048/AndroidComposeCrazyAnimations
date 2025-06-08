package com.xequal2.androidcomposecrazyanimations.navigation

sealed class Screen(val route: String) {
    object ParticleSphere : Screen("ParticleSphere")
    object ParticleSphere2 : Screen("ParticleSphere2")
    object ScreenContent : Screen("ScreenContent")
    object ParticleScreenContent : Screen("ParticleScreenContent")
    object ExplodingSphereCanvas : Screen("ExplodingSphereCanvas")
    object TouchGravityCanvas : Screen("TouchGravityCanvas")
    object ShapeMorphingCanvas : Screen("ShapeMorphingCanvas")


    object PongWarsGame : Screen("PongWarsGame")

    object DotsAndLinesComposeView : Screen("DotsAndLinesComposeView")
}
