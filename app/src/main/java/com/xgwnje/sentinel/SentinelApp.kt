package com.xgwnje.sentinel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
// Remove AppNavRail import if no longer used elsewhere, or keep if still previewing it
// import com.xgwnje.sentinel.ui.components.AppNavRail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.xgwnje.sentinel.navigation.AppNavigation
import com.xgwnje.sentinel.ui.components.CustomNavBar // Import your new Nav Bar
import com.xgwnje.sentinel.ui.theme.SentinelTheme

@Composable
fun SentinelApp() {
    val navController = rememberNavController()

    val persistentInsets = WindowInsets.navigationBars.union(WindowInsets.displayCutout)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(persistentInsets)
    ) {
        CustomNavBar( // Use the new CustomNavBar
            navController = navController,
            modifier = Modifier.fillMaxHeight() // It will fill the height of its slot in the Row
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            AppNavigation(navController = navController)
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape"
)
@Composable
fun SentinelAppPreview() {
    SentinelTheme {
        SentinelApp()
    }
}