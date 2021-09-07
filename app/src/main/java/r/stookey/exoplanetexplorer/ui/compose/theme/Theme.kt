package r.stookey.exoplanetexplorer.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkThemeColors = darkColors(
    primary = Primary,
    primaryVariant = PrimaryDark,
    secondary = Secondary,
    secondaryVariant = SecondaryDark,
    background = Color.Black,
    surface = Gray,
    onPrimary = PrimaryTextColor,
    onSecondary = SecondaryTextColor,
    onBackground = Color.Black,
    onSurface = PrimaryTextColor
)

private val LightThemeColors = lightColors(
    primary = Primary,
    primaryVariant = PrimaryLight,
    secondary = Secondary,
    secondaryVariant = SecondaryLight,
    background = Color.White,
    surface = Color.White,
    onPrimary = SecondaryTextColor,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun ExoplanetExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}