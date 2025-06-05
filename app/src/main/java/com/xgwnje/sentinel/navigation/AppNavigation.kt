package com.xgwnje.sentinel.navigation // Ensure this package is correct

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xgwnje.sentinel.ui.preview.PreviewScreen // Will create this screen Composable later
import com.xgwnje.sentinel.ui.settings.SettingsScreen // Will create this screen Composable later
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier // Pass modifier for NavHost styling if needed
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Preview.route, // Default screen to show
        modifier = modifier
    ) {
        composable(Screen.Preview.route) {
            // Composable function for the Preview screen
            PreviewScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            // Composable function for the Settings screen
            SettingsScreen(navController = navController)
        }

        // Add more destinations here as your app grows
        // composable(Screen.VideoLog.route) {
        //    VideoLogScreen(navController = navController)
        // }
    }
}