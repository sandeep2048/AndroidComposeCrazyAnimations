
@file:OptIn(ExperimentalComposeUiApi::class)

package com.xequal2.androidcomposecrazyanimations.domainmorping

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CloudOrbWithControls() {
    val shaderCode = remember {
        """
        uniform float2 resolution;
        uniform float time;
        uniform float2 direction;
        uniform float speed;

        float hash(float2 p) {
            return fract(sin(dot(p, float2(127.1, 311.7))) * 43758.5453123);
        }

        float noise(float2 p) {
            float2 i = floor(p);
            float2 f = fract(p);
            float a = hash(i);
            float b = hash(i + float2(1.0, 0.0));
            float c = hash(i + float2(0.0, 1.0));
            float d = hash(i + float2(1.0, 1.0));
            float2 u = f * f * (3.0 - 2.0 * f);
            return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
        }

        float fbm(float2 p) {
            float v = 0.0;
            v += 0.5 * noise(p);
            p *= 2.0;
            v += 0.25 * noise(p);
            p *= 2.0;
            v += 0.125 * noise(p);
            p *= 2.0;
            v += 0.0625 * noise(p);
            return v;
        }

        half4 main(float2 fragCoord) {
            float2 uv = fragCoord / resolution;
            uv = uv * 2.0 - 1.0;
            uv *= float2(resolution.x / resolution.y, 1.0);

            float len = length(uv);
            if (len > 1.0) return half4(0.0);

            float2 p = uv * 2.0 + direction * time * speed;
            float clouds = fbm(p);

            half3 baseColor = half3(0.2, 0.6, 1.0);
            half3 finalColor = baseColor + clouds * 0.3;

            return half4(finalColor, 1.0);
        }
        """.trimIndent()
    }

    val shader = remember { RuntimeShader(shaderCode) }

    val time by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseScale by rememberInfiniteTransition().animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )

    var direction by remember { mutableStateOf(Offset(0f, 0f)) }
    var speed by remember { mutableStateOf(1.0f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { direction = Offset(0f, -0.1f) }) { Text("↑") }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { direction = Offset(-0.1f, 0f) }) { Text("←") }
            Button(onClick = { direction = Offset(0.1f, 0f) }) { Text("→") }
        }

        Canvas(
            modifier = Modifier
                .padding(16.dp)
                .size(280.dp)
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                }
        ) {
            shader.setFloatUniform("resolution", size.width, size.height)
            shader.setFloatUniform("time", time)
            shader.setFloatUniform("direction", direction.x, direction.y)
            shader.setFloatUniform("speed", speed)

            drawCircle(
                color = Color(0x8020AFFF),
                radius = size.minDimension / 2 * 1.2f,
                center = center,
                alpha = 0.5f
            )

            drawRect(
                brush = ShaderBrush(shader),
                size = size
            )
        }

        Button(onClick = { direction = Offset(0f, 0.1f) }) { Text("↓") }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { speed = maxOf(0.1f, speed - 0.1f) }) { Text("Speed ↓") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { speed += 0.1f }) { Text("Speed ↑") }
        }
    }
}
