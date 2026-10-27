package com.vanyookk.unipocket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccent,
    onPrimary = DarkBgPage,
    primaryContainer = DarkBgSurfaceRaised,
    onPrimaryContainer = DarkTextHeading,
    secondary = DarkTextBody,
    onSecondary = DarkBgPage,
    tertiary = DarkTextMuted,
    background = DarkBgPage,
    onBackground = DarkTextHeading,
    surface = DarkBgSurface,
    onSurface = DarkTextHeading,
    surfaceVariant = DarkBgSurfaceRaised,
    onSurfaceVariant = DarkTextBody,
    outline = DarkTextMuted,
    outlineVariant = DarkBgSurfaceRaised
)

private val LightColorScheme = lightColorScheme(
    primary = LightTextAccent,
    onPrimary = Color.White,
    primaryContainer = LightBgSurfaceRaised,
    onPrimaryContainer = LightTextAccent,
    secondary = LightTextMuted,
    onSecondary = Color.White,
    tertiary = LightTextMuted,
    background = LightBgSurface,
    onBackground = LightTextBody,
    surface = LightBgSurface,
    onSurface = LightTextBody,
    surfaceVariant = LightBgSurfaceRaised,
    onSurfaceVariant = LightTextMuted,
    outline = LightBorderDefault,
    outlineVariant = LightBorderDefault
)

@Composable
fun UninetTheme(
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
