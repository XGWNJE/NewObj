package com.xgwnje.sentinel.ui.theme

import android.app.Activity
// import androidx.compose.foundation.isSystemInDarkTheme // Keep if you plan "Follow System"
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes // Make sure Shapes is imported
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.xgwnje.sentinel.data.AppTheme

// Light Color Scheme Definition (assumes colors are defined in Color.kt)
private val SentinelLightColorScheme = lightColorScheme(
    primary = PrimaryAccent,
    onPrimary = OnPrimaryAccent,
    primaryContainer = LightThemePrimaryContainer,
    onPrimaryContainer = OnLightThemePrimaryContainer,

    background = LightBackground,
    onBackground = OnLightBackground,
    surface = LightSurface,
    onSurface = OnLightSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = OnLightSurfaceVariantText, // Use the specific text color for surfaceVariant

    error = ErrorRedLight,
    onError = OnErrorRedLight,
    errorContainer = ErrorRedLight.copy(alpha = 0.2f), // Example error container
    onErrorContainer = OnErrorRedLight,              // Example text on error container

    outline = DividerColorLight
    // Define other roles like secondary, tertiary, and their containers if/when used
)

// Dark Color Scheme Definition (assumes colors are defined in Color.kt)
private val SentinelDarkColorScheme = darkColorScheme(
    primary = PrimaryAccent,
    onPrimary = OnPrimaryAccent,
    primaryContainer = DarkThemePrimaryContainer,
    onPrimaryContainer = OnDarkThemePrimaryContainer,

    background = DarkBackground,
    onBackground = OnDarkBackground,
    surface = DarkSurface,
    onSurface = OnDarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSurfaceVariantText, // Use the specific text color for surfaceVariant

    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorRed.copy(alpha = 0.2f), // Example error container
    onErrorContainer = OnDarkSurface,             // Example text on error container

    outline = DividerColor
    // Define other roles
)

@Composable
fun SentinelTheme(
    currentAppTheme: AppTheme = AppTheme.DARK, // Default for previews if not overridden
    content: @Composable () -> Unit
) {
    val colorScheme = when (currentAppTheme) {
        AppTheme.LIGHT -> SentinelLightColorScheme
        AppTheme.DARK -> SentinelDarkColorScheme
        // Handle AppTheme.SYSTEM here if you add it in the future:
        // AppTheme.SYSTEM -> if (isSystemInDarkTheme()) SentinelDarkColorScheme else SentinelLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Determine if the current effective theme is light for system bar icon colors
            val isEffectivelyLightTheme = when (currentAppTheme) {
                AppTheme.LIGHT -> true
                AppTheme.DARK -> false
                // AppTheme.SYSTEM -> !isSystemInDarkTheme() // If SYSTEM theme is added
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isEffectivelyLightTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = isEffectivelyLightTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Assumes AppTypography is defined in your Type.kt
        shapes = AppShapes,         // APPLY THE GLOBAL RECTANGULAR SHAPES from Shape.kt
        content = content
    )
}