package com.xgwnje.sentinel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// import androidx.compose.foundation.isSystemInDarkTheme // Not strictly needed if not using "Follow System" yet
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.xgwnje.sentinel.data.AppTheme
import com.xgwnje.sentinel.data.ThemeDataStore // Make sure this is the correct path to your ThemeDataStore
import com.xgwnje.sentinel.ui.theme.SentinelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Call before super.onCreate()
        super.onCreate(savedInstanceState)

        // ThemeDataStore instance
        val themeDataStore = ThemeDataStore(applicationContext) // Use applicationContext

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ) { resources.configuration.isNightModeActive }, // Let it adapt for now
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ) { resources.configuration.isNightModeActive } // Let it adapt for now
        )

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            // Collect the theme preference from DataStore, defaulting to DARK if nothing is stored yet
            val currentAppTheme by themeDataStore.appThemeFlow.collectAsState(initial = AppTheme.DARK)

            SentinelTheme(currentAppTheme = currentAppTheme) { // Pass the observed theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SentinelApp()
                }
            }
        }
    }
}