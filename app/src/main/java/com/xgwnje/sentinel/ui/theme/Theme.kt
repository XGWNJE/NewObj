package com.xgwnje.sentinel.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
// import androidx.compose.ui.graphics.Color // Only needed if Color.Transparent.toArgb() is used
import androidx.compose.ui.platform.LocalView // Removed LocalContext if not used for dynamicColor
import androidx.core.view.WindowCompat

// (SentinelDarkColorScheme definition remains the same as before)
private val SentinelDarkColorScheme = darkColorScheme(
    primary = PrimaryAccent,
    onPrimary = OnPrimaryAccent,
    background = DarkBackground,
    onBackground = OnDarkBackground,
    surface = DarkSurface,
    onSurface = OnDarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSurface,
    error = ErrorRed,
    onError = OnErrorRed,
    outline = DividerColor,
)


@Composable
fun SentinelTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = SentinelDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // enableEdgeToEdge() in MainActivity will handle making the bars transparent.
            // This SideEffect ensures the icon appearance is correct for our dark theme.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}