package com.umang.reminderapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF267e9b),
    onPrimary = Color(0xFFe8fcfd),

    primaryContainer = Color(0xFF144352),
    onPrimaryContainer = Color(0xFFd6edf5),

    inversePrimary = Color(0xFF0a2129),

    secondary = Color(0xFF70a8b0),
    onSecondary = Color(0xFF2d4d52),

    secondaryContainer = Color(0xFF121f21),
    onSecondaryContainer = Color(0xFFd6edf5),

    tertiary = Color(0xFFee5c53),
    onTertiary = Color(0xFFfad3d1),
    tertiaryContainer = Color(0xFF8b140e),
    onTertiaryContainer = Color(0xFFfad3d1),

    error = red40,
    onError = red10,
    errorContainer = red30,
    onErrorContainer = red90,

    background = Color(0xFF0c2535),
    onBackground = Color(0xFF70a8b0),

    surface = Color(0xFF184968),
    onSurface = Color(0xFFe8fcfd),
    inverseSurface = Color(0xFF0c2535),
    inverseOnSurface = Color(0xFFe8fcfd),
    surfaceVariant = Color(0xFF1c587d),
    onSurfaceVariant = Color(0xFFeaf4fa),
    outline = grey80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0c7f88),
    onPrimary = Color(0xFFe8fcfd),

    primaryContainer = Color(0xFF10b0bc),
    onPrimaryContainer = Color(0xFFe8fcfd),

    inversePrimary = Color(0xFF0a6e75),

    secondary = Color(0xFF424749),
    onSecondary = Color(0xFFf2f3f3),

    secondaryContainer = Color(0xFF10b0bc),
    onSecondaryContainer = Color(0xFF303436),

    tertiary = Color(0xFFe0523e),
    onTertiary = Color(0xFFfbebe9),

    tertiaryContainer = Color(0xFFea887b),
    onTertiaryContainer = Color(0xFFfcebe9),

    error = red40,
    onError = red10,

    errorContainer = red30,
    onErrorContainer = red90,

    background = Color(0xFFfefbf5),
    onBackground = Color(0xFF424749),

    surface = Color(0xFFfefbf5),
    onSurface = Color(0xFF0c7f88),

    surfaceContainerLow = Color(0xFF10b0bc),

    inverseSurface = grey90,
    inverseOnSurface = grey10,

)

@Composable
fun ReminderAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}