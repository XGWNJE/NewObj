package com.xgwnje.sentinel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.xgwnje.sentinel.ui.theme.SentinelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Install SplashScreen (handles transition from system splash to app theme)
        // This should be called before super.onCreate() or at least before setContent.
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // 2. Enable edge-to-edge display. This will:
        //    - Call WindowCompat.setDecorFitsSystemWindows(window, false)
        //    - Set system bars to be transparent.
        //    - Handle icon contrast for system bars.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto( // Effectively transparent, icons adapt
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
                detectDarkMode = { resources.configuration.isNightModeActive } // Or fixed { true } for light icons
            ),
            navigationBarStyle = SystemBarStyle.auto( // Similar for nav bar
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
                detectDarkMode = { resources.configuration.isNightModeActive }
            )
        )

        // 3. Explicitly hide the status bar using WindowInsetsController.
        // This is done after edge-to-edge setup to ensure the window is already
        // prepared to draw behind where the status bar would be.
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        // 4. Set Compose content.
        // By this point, the window should be fully configured for edge-to-edge
        // with the status bar hidden.
        setContent {
            SentinelTheme {
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