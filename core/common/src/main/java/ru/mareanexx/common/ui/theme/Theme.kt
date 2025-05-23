package ru.mareanexx.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkColorPrimary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    onPrimary = DarkOnPrimary,
    surface = DarkSurface,
    surfaceBright = DarkSurfaceBright,
    onSurface = DarkOnSurface,
    surfaceContainer = LightSurfaceContainer,
    surfaceContainerHigh = LightSurfaceContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = LightColorPrimary,
    background = LightBackground,
    onBackground = LightOnBackground,
    onPrimary = LightOnPrimary,
    surface = LightSurface,
    surfaceBright = LightSurfaceBright,
    onSurface = LightOnSurface,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary
)

@Composable
fun JotterBlogAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}