package com.xgwnje.sentinel.ui.settings

// Ensure all necessary imports are present
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Make sure this specific items is imported
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xgwnje.sentinel.R
import com.xgwnje.sentinel.ui.theme.SentinelTheme

// Enum for different setting categories
// This definition should be exactly as before and accessible in this file's scope.
enum class SettingCategory(val titleResId: Int) {
    MODEL(R.string.settings_category_model),
    NOTIFICATIONS(R.string.settings_category_notifications),
    BLE_CONNECTION(R.string.settings_category_ble_connection),
    SUBSCRIPTION(R.string.settings_category_subscription),
    VIDEO_LOG(R.string.settings_category_video_log),
    BACKGROUND_OPTIMIZATION(R.string.settings_category_background_optimization)
}

@Composable
fun SettingsScreen(
    @Suppress("UNUSED_PARAMETER") navController: NavController,
    modifier: Modifier = Modifier
) {
    // Explicitly type the state variable
    var selectedCategory: SettingCategory by remember { mutableStateOf(SettingCategory.MODEL) }
    val categories: List<SettingCategory> = SettingCategory.entries // Using .entries, which returns List<SettingCategory>

    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Left Pane (Master - Settings Categories)
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.35f),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            tonalElevation = 2.dp
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(categories) { category -> // 'category' here is correctly typed as SettingCategory
                    val categoryTitle = stringResource(id = category.titleResId) // category.titleResId should resolve
                    SettingsCategoryItem( // This Composable needs to be defined in this file or imported
                        title = categoryTitle,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                    HorizontalDivider()
                }
            }
        }

        // Right Pane (Detail - Content for selected category)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.65f)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            val selectedCategoryTitle = stringResource(id = selectedCategory.titleResId) // selectedCategory.titleResId should resolve
            Text(
                "$selectedCategoryTitle - 内容区域 (占位符)",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

// This Composable was part of the previous SettingsScreen.kt, ensure it's present and correct.
@Composable
fun SettingsCategoryItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Added modifier as good practice
) {
    Surface(
        modifier = modifier // Applied modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent, // Use androidx.compose.ui.graphics.Color
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun SettingsScreenPreview() {
    SentinelTheme {
        SettingsScreen(navController = rememberNavController())
    }
}