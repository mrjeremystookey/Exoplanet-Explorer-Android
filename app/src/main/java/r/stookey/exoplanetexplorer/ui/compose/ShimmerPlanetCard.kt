package r.stookey.exoplanetexplorer.ui.compose

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ShimmerPlanetCard(cardHeight: Dp){

    val infiniteTransition = rememberInfiniteTransition()
    val xShimmer = infiniteTransition.animateFloat(initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 850,
            easing = LinearOutSlowInEasing
        )
    ))
    val yShimmer = infiniteTransition.animateFloat(initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 850,
            easing = LinearOutSlowInEasing
        )
    ))

    val colors = listOf(
        MaterialTheme.colors.primaryVariant.copy(alpha = 0.9f),
        MaterialTheme.colors.secondary.copy(alpha = 0.5f),
        MaterialTheme.colors.primaryVariant.copy(alpha = 0.9f))

    val brush = Brush.linearGradient(
        colors,
        start = Offset(xShimmer.value - 200, yShimmer.value - 200),
        end = Offset(xShimmer.value + 200, yShimmer.value + 200)
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(bottom = 6.dp, top = 6.dp) ){
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .background(brush = brush)
        )
    }
}


@Preview
@Composable
fun previewShimerPlanetCard(){
    ShimmerPlanetCard(cardHeight = 55.dp)
}