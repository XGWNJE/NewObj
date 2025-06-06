package com.xgwnje.sentinel.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xgwnje.sentinel.R
import com.xgwnje.sentinel.data.AppTheme
import com.xgwnje.sentinel.ui.theme.SentinelTheme

// 主屏幕，实现带细线分隔的自适应布局
@Composable
fun PreviewScreen(
    @Suppress("UNUSED_PARAMETER") navController: NavController,
    modifier: Modifier = Modifier
) {
    // --- 模拟状态变量 ---
    var isPreviewing by remember { mutableStateOf(true) }
    var isMonitoring by remember { mutableStateOf(false) }
    var isAlarmActive by remember { mutableStateOf(false) }

    LaunchedEffect(isMonitoring) {
        if (isMonitoring) {
            kotlinx.coroutines.delay(3000)
            isAlarmActive = true
        } else {
            isAlarmActive = false
        }
    }
    // --- 模拟状态变量结束 ---

    // 根布局为水平排列 (Row)
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. 左侧：主预览区域
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(4 / 3f, matchHeightConstraintsFirst = true) // 保持 4:3 的宽高比
                .background(Color.Black)
        ) {
            if (isPreviewing) {
                CameraPreviewPlaceholder()
            } else {
                PreviewPausedPlaceholder()
            }
        }

        // 细线分隔
        VerticalDivider()

        // 2. 右侧：状态面板和控制按钮，自适应宽度
        Column(
            modifier = Modifier
                .fillMaxSize() // 填充所有剩余空间
        ) {
            // 状态面板，占据大部分垂直空间
            MonitoringStatusPanel(
                isMonitoring = isMonitoring,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // 核心修改：让状态面板占据所有可用的垂直权重
            )

            // 细线分隔
            HorizontalDivider()

            // 快捷按钮，高度由其内容决定
            ActionButtons(
                isPreviewing = isPreviewing,
                isMonitoring = isMonitoring,
                isAlarmActive = isAlarmActive,
                onPreviewToggle = { isPreviewing = !isPreviewing },
                onMonitoringToggle = { isMonitoring = !isMonitoring },
                onResetAlarm = { isAlarmActive = false }
            )
        }
    }
}

// 区域1: 摄像头预览占位符
@Composable
private fun CameraPreviewPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 在实际应用中，这里会被 CameraX 的 PreviewView 替代
    }
}

// 区域1: 预览暂停时的占位符
@Composable
private fun PreviewPausedPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.VideocamOff,
                contentDescription = stringResource(R.string.preview_camera_placeholder),
                modifier = Modifier.size(96.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "预览已暂停",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.LightGray
            )
        }
    }
}

// 区域2 (右侧): 监控状态面板
@Composable
private fun MonitoringStatusPanel(isMonitoring: Boolean, modifier: Modifier = Modifier) {
    // 核心修改：添加 verticalScroll 使内容可滑动
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // 使内容可上下滑动
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "监控状态",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))
        Text("模式: 主机模式", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("蓝牙: 已连接 1 个从机", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("AI识别: ${if (isMonitoring) "已开启 - 车辆" else "未启动"}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("当前事件: ${if (isMonitoring) "检测到移动" else "无"}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        // 增加额外文本以测试滑动效果
        Text("置信度: 92%", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("录制状态: 未录制", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("存储空间: 5.2 / 10.0 GB", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("上次警报: 10:35 AM", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("系统负载: 35%", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text("网络状态: 已连接", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}


// 区域2 (右侧): 快捷按钮区域 (垂直排列)
@Composable
private fun ActionButtons(
    isPreviewing: Boolean,
    isMonitoring: Boolean,
    isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit,
    onMonitoringToggle: () -> Unit,
    onResetAlarm: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionButton(
            text = if (isPreviewing) "停止预览" else "开始预览",
            icon = if (isPreviewing) Icons.Default.VisibilityOff else Icons.Default.Visibility,
            onClick = onPreviewToggle
        )
        ActionButton(
            text = if (isMonitoring) "暂停监控" else "开始监控",
            icon = if (isMonitoring) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline,
            onClick = onMonitoringToggle,
            isPrimary = true
        )
        ActionButton(
            text = "复位警报",
            icon = Icons.Default.Refresh,
            onClick = onResetAlarm,
            enabled = isAlarmActive
        )
    }
}

// 可复用的快捷按钮组件
@Composable
private fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isPrimary: Boolean = false
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    } else {
        ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    }

    val buttonToShow: @Composable RowScope.() -> Unit = {
        Icon(imageVector = icon, contentDescription = text)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }

    if (isPrimary) {
        Button(
            onClick = onClick, enabled = enabled, shape = RectangleShape,
            modifier = modifier.fillMaxWidth(), colors = buttonColors,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            buttonToShow()
        }
    } else {
        OutlinedButton(
            onClick = onClick, enabled = enabled, shape = RectangleShape,
            modifier = modifier.fillMaxWidth(), colors = buttonColors,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            buttonToShow()
        }
    }
}


// 自定义细线分隔
@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    )
}

@Composable
private fun HorizontalDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    )
}


// --- 预览 ---
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape", name="Preview Screen Dark Theme")
@Composable
fun PreviewScreenPreviewDark() {
    SentinelTheme(currentAppTheme = AppTheme.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PreviewScreen(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape", name="Preview Screen Light Theme")
@Composable
fun PreviewScreenPreviewLight() {
    SentinelTheme(currentAppTheme = AppTheme.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PreviewScreen(navController = rememberNavController())
        }
    }
}
