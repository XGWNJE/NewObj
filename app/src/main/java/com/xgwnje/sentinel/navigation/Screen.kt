package com.xgwnje.sentinel.navigation // Ensure this package is correct

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt // Changed from Camera for clarity
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Sealed class to define app navigation routes
sealed class Screen(
    val route: String,
    val titleResId: Int, // Using String resource ID for localization
    val icon: ImageVector // Icon for navigation (e.g., NavRail)
) {
    object Preview : Screen(
        route = "preview",
        titleResId = com.xgwnje.sentinel.R.string.nav_title_preview, // Reference strings.xml
        icon = Icons.Filled.CameraAlt
    )

    object Settings : Screen(
        route = "settings",
        titleResId = com.xgwnje.sentinel.R.string.nav_title_settings, // Reference strings.xml
        icon = Icons.Filled.Settings
    )

    // Add other main screens here as needed
    // For example:
    // object VideoLog : Screen("video_log", R.string.nav_title_video_log, Icons.Filled.VideoLibrary)
}

// List of top-level navigation items for easy iteration (e.g., in NavRail)
val topLevelScreens = listOf(
    Screen.Preview,
    Screen.Settings,
    // Screen.VideoLog, // Add if you have more top-level screens
)