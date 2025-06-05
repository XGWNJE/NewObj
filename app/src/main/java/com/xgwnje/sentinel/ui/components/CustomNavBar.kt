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
import androidx.compose.ui.graphics.vector.ImageVector // Correct import
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Import for sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        topLevelScreens.forEach { screen ->
            val screenTitle = stringResource(id = screen.titleResId)
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        color = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        else Color.Transparent,
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
    icon: ImageVector, // Parameter type
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 4.dp), // Increased vertical padding for larger content
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(36.dp), // INCREASED ICON SIZE
            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp)) // INCREASED SPACER
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy( // Using labelMedium as base
                fontSize = 13.sp // Custom font size, adjust as needed
            ),
            textAlign = TextAlign.Center,
            maxLines = 2, // Allow text to wrap to two lines if necessary
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, name = "CustomNavBar Standalone")
@Composable
fun CustomNavBarPreview() {
    SentinelTheme {
        val navController = rememberNavController()
        CustomNavBar(
            navController = navController,
            modifier = Modifier
                .width(80.dp) // Typical NavRail width for preview
                .height(300.dp) // Constrained height for preview
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape", name = "CustomNavBar Integration")
@Composable
fun CustomNavBarIntegrationPreview() {
    SentinelTheme {
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
                    .background(Color.DarkGray)
            ) {
                Text("Content Area", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
        }
    }
}