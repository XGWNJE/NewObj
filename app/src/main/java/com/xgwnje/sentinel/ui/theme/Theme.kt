package com.xgwnje.sentinel.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.xgwnje.sentinel.data.AppTheme

// Light Color Scheme Definition
private val SentinelLightColorScheme = lightColorScheme(
    primary = PrimaryAccent,        // Bright teal for specific accents if needed
    onPrimary = OnPrimaryAccent,    // Black for text *on* PrimaryAccent (if PrimaryAccent is background)

    primaryContainer = LightThemePrimaryContainer,     // Light pastel teal background for selected items
    onPrimaryContainer = OnLightThemePrimaryContainer, // Dark teal text/icon on LightThemePrimaryContainer

    background = LightBackground,
    onBackground = OnLightBackground,
    surface = LightSurface,
    onSurface = OnLightSurface,
    surfaceVariant = LightSurfaceVariant,        // e.g., unselected Nav Bar background
    onSurfaceVariant = OnLightSurfaceVariantText, // Text for unselected items

    error = ErrorRedLight,
    onError = OnErrorRedLight,
    errorContainer = ErrorRedLight.copy(alpha = 0.2f), // Example
    onErrorContainer = OnErrorRedLight,                // Example

    outline = DividerColorLight
    // Define other roles like secondary, tertiary as needed
)

// Dark Color Scheme Definition
private val SentinelDarkColorScheme = darkColorScheme(
    primary = PrimaryAccent,         // Bright teal for specific accents
    onPrimary = OnPrimaryAccent,     // Black for text *on* PrimaryAccent

    primaryContainer = DarkThemePrimaryContainer,     // Dark solid teal background for selected items
    onPrimaryContainer = OnDarkThemePrimaryContainer, // Light teal text/icon on DarkThemePrimaryContainer

    background = DarkBackground,
    onBackground = OnDarkBackground,
    surface = DarkSurface,
    onSurface = OnDarkSurface,
    surfaceVariant = DarkSurfaceVariant,        // e.g., unselected Nav Bar background
    onSurfaceVariant = OnDarkSurfaceVariantText, // Text for unselected items

    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorRed.copy(alpha = 0.2f), // Example
    onErrorContainer = OnDarkSurface,             // Example

    outline = DividerColor
    // Define other roles
)

@Composable
fun SentinelTheme(
    currentAppTheme: AppTheme = AppTheme.DARK,
    content: @Composable () -> Unit
) {
    val colorScheme = when (currentAppTheme) {
        AppTheme.LIGHT -> SentinelLightColorScheme
        AppTheme.DARK -> SentinelDarkColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val isEffectivelyLightTheme = when (currentAppTheme) {
                AppTheme.LIGHT -> true
                AppTheme.DARK -> false
                // Handle AppTheme.SYSTEM if added: isSystemInDarkTheme() would be !isLight
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isEffectivelyLightTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = isEffectivelyLightTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}