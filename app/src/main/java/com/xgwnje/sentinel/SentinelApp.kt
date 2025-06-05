package com.xgwnje.sentinel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width // Import width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // Import dp
import androidx.navigation.compose.rememberNavController
import com.xgwnje.sentinel.navigation.AppNavigation
import com.xgwnje.sentinel.ui.components.CustomNavBar
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
        CustomNavBar(
            navController = navController,
            modifier = Modifier
                .width(80.dp) // <<< Explicitly define the width of the Nav Bar
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .weight(1f) // Content area takes the remaining space
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