package ru.verngrov.winelifestyleapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Accent,
    onPrimary = BackgroundCard,
    primaryContainer = AccentLight,
    onPrimaryContainer = Accent,
    background = BackgroundScreen,
    onBackground = TextPrimary,
    surface = BackgroundCard,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundSurface,
    onSurfaceVariant = TextSecondary,
    outline = Border,
    outlineVariant = Border,
)

private val DarkColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = BackgroundCard,
    primaryContainer = AccentLight,
    onPrimaryContainer = Accent,
    background = DarkBrown,
    onBackground = BackgroundScreen,
    surface = DarkBrownDeep,
    onSurface = BackgroundScreen,
    surfaceVariant = DarkBrown,
    onSurfaceVariant = TextTertiary,
    outline = Border,
    outlineVariant = Border,
)

@Composable
fun WineLifestyleAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes,
    )
}