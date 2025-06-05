package com.xgwnje.sentinel.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box // Import Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // Import Row for the integration preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize // Import fillMaxSize for the integration preview
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
// import androidx.compose.foundation.layout.weight // Import weight for the integration preview
import androidx.compose.foundation.shape.CircleShape // CircleShape was for an older design, might not be needed now unless you want circular item backgrounds
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround // This will space out the two items
    ) {
        topLevelScreens.forEach { screen ->
            val screenTitle = stringResource(id = screen.titleResId)
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            CustomNavItem(
                title = screenTitle,
                icon = screen.icon,
                isSelected = selected,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CustomNavItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                else Color.Transparent
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(28.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Preview for the CustomNavBar itself
@Preview(showBackground = true, widthDp = 100, name = "CustomNavBar Only")
@Composable
fun CustomNavBarPreview() {
    SentinelTheme {
        val navController = rememberNavController()
        // Provide a modifier that gives it a constrained height for a realistic preview
        CustomNavBar(navController = navController, modifier = Modifier.height(300.dp))
    }
}

// Preview showing CustomNavBar integrated into a Row with a placeholder content area
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape", name = "CustomNavBar Integration")
@Composable
fun CustomNavBarIntegrationPreview() {
    SentinelTheme {
        Row(Modifier.fillMaxSize()) { // Row needs to be imported
            CustomNavBar(
                navController = rememberNavController(),
                modifier = Modifier.fillMaxHeight() // Nav bar takes full height of its column in the Row
            )
            Box( // Box needs to be imported
                modifier = Modifier
                    .weight(1f) // weight needs to be available (comes from RowScope/ColumnScope)
                    .fillMaxHeight()
                    .background(Color.Gray)
            ) {
                Text("Content Area Placeholder", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}