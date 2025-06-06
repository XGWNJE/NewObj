package com.xgwnje.sentinel.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xgwnje.sentinel.ui.preview.PreviewViewModel
import kotlin.math.roundToInt

@Composable
fun DifferenceSettingsSection(
    viewModel: PreviewViewModel
) {
    // 使用 by 委托需要 getValue()，它由 androidx.compose.runtime.getValue 提供
    val pixelThreshold by viewModel.pixelChangeThreshold.collectAsState()
    val analysisInterval by viewModel.analysisInterval.collectAsState()

    Column {
        Text(
            text = "画面差异监控设置",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        SettingSlider(
            title = "灵敏度阈值",
            value = pixelThreshold,
            onValueChange = viewModel::onPixelThresholdChange,
            valueRange = 5f..30f,
            steps = 24,
            valueSuffix = "%",
            description = "设置单个区域的变化多剧烈才算作有效运动。值越低越灵敏。"
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingSlider(
            title = "分析间隔",
            value = analysisInterval,
            onValueChange = viewModel::onAnalysisIntervalChange,
            valueRange = 15f..90f,
            steps = 74,
            valueSuffix = " 帧",
            description = "设置每隔多少帧分析一次画面。值越高，性能越好，但响应越慢。"
        )
    }
}

@Composable
private fun SettingSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    valueSuffix: String = "",
    description: String
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${value.roundToInt()}$valueSuffix",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps
        )
        Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
