package com.xgwnje.sentinel.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xgwnje.sentinel.data.AppTheme
import com.xgwnje.sentinel.navigation.Screen
import com.xgwnje.sentinel.navigation.topLevelScreens
import com.xgwnje.sentinel.ui.theme.SentinelTheme

@Composable
fun CustomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(
        modifier = modifier
            .fillMaxHeight()
            // Use surfaceVariant for the overall bar background
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        topLevelScreens.forEach { screen ->
            val screenTitle = stringResource(id = screen.titleResId)
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background( // Selected item background
                        color = if (selected) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent, // Or MaterialTheme.colorScheme.surfaceVariant if you want it same as bar
                        shape = RectangleShape
                    )
                    .clickable {
                        if (currentDestination?.route != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                CustomNavItemContent(
                    title = screenTitle,
                    icon = screen.icon,
                    isSelected = selected
                )
            }
        }
    }
}

@Composable
private fun CustomNavItemContent(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(36.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer // Tint for selected icon
            else MaterialTheme.colorScheme.onSurfaceVariant // Tint for unselected icon
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 13.sp
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer // Color for selected text
            else MaterialTheme.colorScheme.onSurfaceVariant // Color for unselected text
        )
    }
}

// Previews ... (keep them as they were, they should reflect the new colors)
@Preview(showBackground = true, name = "CustomNavBar Standalone Dark", widthDp = 80)
@Composable
fun CustomNavBarPreviewDark() {
    SentinelTheme(currentAppTheme = AppTheme.DARK) { // Explicitly preview dark theme
        val navController = rememberNavController()
        CustomNavBar(
            navController = navController,
            modifier = Modifier
                .width(80.dp)
                .height(300.dp)
        )
    }
}

@Preview(showBackground = true, name = "CustomNavBar Standalone Light", widthDp = 80)
@Composable
fun CustomNavBarPreviewLight() {
    SentinelTheme(currentAppTheme = AppTheme.LIGHT) { // Explicitly preview light theme
        val navController = rememberNavController()
        CustomNavBar(
            navController = navController,
            modifier = Modifier
                .width(80.dp)
                .height(300.dp)
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape", name = "CustomNavBar Integration Dark")
@Composable
fun CustomNavBarIntegrationPreviewDark() {
    SentinelTheme(currentAppTheme = AppTheme.DARK) {
        Row(Modifier.fillMaxSize()) {
            CustomNavBar(
                navController = rememberNavController(),
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background) // Use theme background
            ) {
                Text("Content Area", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}