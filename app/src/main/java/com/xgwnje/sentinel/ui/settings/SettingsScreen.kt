package com.xgwnje.sentinel.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xgwnje.sentinel.R
import com.xgwnje.sentinel.ui.theme.SentinelTheme

// Enum SettingCategory remains the same
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
    var selectedCategory: SettingCategory by remember { mutableStateOf(SettingCategory.MODEL) }
    val categories: List<SettingCategory> = SettingCategory.entries

    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Left Pane (Master - Settings Categories)
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(175.dp), // <<< ADJUSTED WIDTH (approx. 2/3 of 260dp)
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
            tonalElevation = 1.dp
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                items(categories) { category ->
                    val categoryTitle = stringResource(id = category.titleResId)
                    SettingsCategoryItem(
                        title = categoryTitle,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                    // HorizontalDivider() // Optional
                }
            }
        }

        // Right Pane (Detail - Content for selected category)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f) // Takes remaining width
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            val selectedCategoryTitle = stringResource(id = selectedCategory.titleResId)
            Column {
                Text(
                    selectedCategoryTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "详细内容区域 ($selectedCategoryTitle) - 占位符",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun SettingsCategoryItem( // This Composable remains unchanged from the last version
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                else Color.Transparent,
                shape = RectangleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 20.dp)
        ,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurfaceVariant
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