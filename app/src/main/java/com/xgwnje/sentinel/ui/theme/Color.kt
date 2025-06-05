package com.xgwnje.sentinel.ui.theme

import androidx.compose.ui.graphics.Color

// --- Dark Theme Palette ---
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkSurfaceVariant = Color(0xFF2C2C2C) // Used for NavRail bg, Settings master pane bg

val PrimaryAccent = Color(0xFF00ACC1)    // Bright Teal/Cyan (e.g., for selected text/icon accents)
val OnPrimaryAccent = Color(0xFF000000)  // Black (for text on PrimaryAccent if PrimaryAccent is very light)

val OnDarkBackground = Color(0xFFE0E0E0)  // Light Gray (main text on dark background)
val OnDarkSurface = Color(0xFFE0E0E0)     // Light Gray (main text on dark surfaces)
// For unselected items in NavBars/Lists if their background is surfaceVariant
val OnDarkSurfaceVariantText = Color(0xFFB0B0B0) // Slightly dimmer light gray for unselected text

val ErrorRed = Color(0xFFCF6679)
val OnErrorRed = Color(0xFF000000)
val DividerColor = Color(0x29FFFFFF)

// NEW/ADJUSTED for Dark Theme Selected States
val DarkThemePrimaryContainer = Color(0xFF004A54) // A darker, more solid teal/blue
val OnDarkThemePrimaryContainer = Color(0xFFB3E5FC) // A light, contrasting cyan/blue for text/icons

// --- Light Theme Palette ---
val LightBackground = Color(0xFFFDFDFD)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF0F0F0) // Light gray for NavRail bg, Settings master pane bg

// PrimaryAccent can be shared, or define PrimaryAccentLight if different
// OnPrimaryAccent can be shared, or define OnPrimaryAccentLight

val OnLightBackground = Color(0xFF19191C)     // Dark Gray/Black (main text on light background)
val OnLightSurface = Color(0xFF19191C)        // Dark Gray/Black (main text on light surfaces)
// For unselected items in NavBars/Lists
val OnLightSurfaceVariantText = Color(0xFF545454) // Medium-Dark gray for unselected text

val ErrorRedLight = Color(0xFFB00020)
val OnErrorRedLight = Color(0xFFFFFFFF)
val DividerColorLight = Color(0x1F000000)

// NEW/ADJUSTED for Light Theme Selected States
val LightThemePrimaryContainer = Color(0xFFA6D8E0) // A light, pastel teal/blue
val OnLightThemePrimaryContainer = Color(0xFF003138)   // A dark, contrasting teal/blue for text/icons

// Standard Colors
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Transparent = Color(0x00000000)