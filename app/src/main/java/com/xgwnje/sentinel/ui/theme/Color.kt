package com.xgwnje.sentinel.ui.theme

import androidx.compose.ui.graphics.Color

// Dark Theme Palette for Sentinel App

// Base Colors
val DarkBackground = Color(0xFF121212)       // Deep, near-black for overall background
val DarkSurface = Color(0xFF1E1E1E)         // Slightly lighter gray for component surfaces (cards, dialogs)
val DarkSurfaceVariant = Color(0xFF2C2C2C)   // Another variant for surfaces if needed for depth

// Primary Accent Color (Tech Blue / Muted Teal)
val PrimaryAccent = Color(0xFF00ACC1)       // A distinct but not overly bright teal/blue
val OnPrimaryAccent = Color(0xFF000000)     // Text/icons on PrimaryAccent (Black for good contrast)

// Secondary Accent Color (Optional - could be a subtler gray or a complementary color)
// val SecondaryAccent = Color(0xFF03DAC6)  // Example if needed
// val OnSecondaryAccent = Color(0xFF000000) // Example if needed

// Text & Icon Colors on Main Surfaces
val OnDarkBackground = Color(0xFFE0E0E0)     // Light gray for text on DarkBackground (not pure white)
val OnDarkSurface = Color(0xFFE0E0E0)        // Light gray for text on DarkSurface

// Error Colors
val ErrorRed = Color(0xFFCF6679)            // Material standard error color for dark themes
val OnErrorRed = Color(0xFF000000)          // Text/icons on ErrorRed

// Other UI Colors
val NavRailBackground = Color(0xFF1A1A1A)    // Slightly different for the navigation rail
val DividerColor = Color(0x29FFFFFF)       // Semi-transparent white for dividers (16% opacity)

// Specific status colors if needed (e.g., for indicators)
val StatusGreen = Color(0xFF66BB6A)
val StatusYellow = Color(0xFFFFEE58)
val StatusOrange = Color(0xFFFFA726)

// Standard Colors (often useful as fallbacks or for specific non-themed elements)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Transparent = Color(0x00000000)