package com.xgwnje.sentinel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.xgwnje.sentinel.ui.preview.PreviewScreen
import com.xgwnje.sentinel.ui.preview.PreviewViewModel
import com.xgwnje.sentinel.ui.settings.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "main_graph",
        modifier = modifier
    ) {
        navigation(
            startDestination = Screen.Preview.route,
            route = "main_graph"
        ) {
            composable(Screen.Preview.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("main_graph")
                }
                val previewViewModel: PreviewViewModel = viewModel(parentEntry)

                PreviewScreen(navController = navController, viewModel = previewViewModel)
            }

            composable(Screen.Settings.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("main_graph")
                }
                val previewViewModel: PreviewViewModel = viewModel(parentEntry)

                SettingsScreen(navController = navController, previewViewModel = previewViewModel)
            }
        }
    }
}
