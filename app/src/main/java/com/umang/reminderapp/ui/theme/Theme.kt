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
    primary = cyan10,
    onPrimary = cyan80,
    primaryContainer = cyan30,
    onPrimaryContainer = cyan90,
    inversePrimary = cyan40,
    secondary = darkblue10,
    onSecondary = darkblue80,
    secondaryContainer = darkblue30,
    onSecondaryContainer = darkblue90,
    tertiary = lightflesh80,
    onTertiary = lightflesh20,
    tertiaryContainer = lightflesh30,
    onTertiaryContainer = lightflesh90,
    error = red40,
    onError = red10,
    errorContainer = red30,
    onErrorContainer = red90,
    background = grey20,
    onBackground = grey90,
    surface = grey30,
    onSurface = grey80,
    inverseSurface = grey90,
    inverseOnSurface = grey20,
    surfaceVariant = grey30,
    onSurfaceVariant = grey80,
    outline = grey80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0c7f88),
    onPrimary = Color(0xFF0c7f88),

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