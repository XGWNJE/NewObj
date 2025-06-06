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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xgwnje.sentinel.R
import com.xgwnje.sentinel.ui.preview.PreviewViewModel // 导入 ViewModel

enum class SettingCategory(val titleResId: Int) {
    GENERAL(R.string.settings_category_general),
    DIFFERENCE_CHECK(R.string.settings_category_model), // 将“模型”改为“画面差异检查”
    // ... 其他类别
}

@Composable
fun SettingsScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel // 接收共享的 ViewModel
) {
    var selectedCategory: SettingCategory by remember { mutableStateOf(SettingCategory.GENERAL) }
    val categories = SettingCategory.values()

    Row(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxHeight().width(175.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 1.dp
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(categories) { category ->
                    SettingsCategoryItem(
                        title = stringResource(id = category.titleResId),
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxHeight().weight(1f).padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            when (selectedCategory) {
                // TODO: 实现通用设置
                SettingCategory.GENERAL -> Text("通用设置内容区域")
                // 将阈值设置UI集成到此处
                SettingCategory.DIFFERENCE_CHECK -> DifferenceSettingsSection(viewModel = previewViewModel)
                else -> Text("其他设置内容区域")
            }
        }
    }
}

@Composable
fun SettingsCategoryItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                shape = RectangleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
