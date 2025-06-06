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
import androidx.compose.ui.platform.LocalConfiguration
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

// 主屏幕，实现能根据屏幕宽高比动态切换布局的逻辑
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

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val screenAspectRatio = screenWidthDp.value / screenHeightDp.value

    val useWideLayout = screenAspectRatio > 1.8f

    if (useWideLayout) {
        // 宽屏 (>16:9) 布局
        LandscapeWideLayout(
            isPreviewing, isMonitoring, isAlarmActive,
            { isPreviewing = !isPreviewing }, { isMonitoring = !isMonitoring }, { isAlarmActive = false }
        )
    } else {
        // 窄屏 (<=16:9) "倒品字型" 布局
        LandscapeNarrowLayout(
            isPreviewing, isMonitoring, isAlarmActive,
            { isPreviewing = !isPreviewing }, { isMonitoring = !isMonitoring }, { isAlarmActive = false }
        )
    }
}

// 布局A：适用于宽屏设备 (>16:9) 的左右并列布局
@Composable
private fun LandscapeWideLayout(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(4 / 3f, matchHeightConstraintsFirst = true)
                .background(Color.Black)
        ) {
            if (isPreviewing) CameraPreviewPlaceholder() else PreviewPausedPlaceholder()
        }
        VerticalDivider()
        Column(modifier = Modifier.fillMaxSize()) {
            MonitoringStatusPanel(isMonitoring, modifier = Modifier.weight(1f))
            HorizontalDivider()
            ActionButtonsVertical(
                isPreviewing, isMonitoring, isAlarmActive,
                onPreviewToggle, onMonitoringToggle, onResetAlarm
            )
        }
    }
}

// 布局B：适用于标准/窄屏设备 (<=16:9) 的 "倒品字型" 布局
@Composable
private fun LandscapeNarrowLayout(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(4 / 3f, matchHeightConstraintsFirst = true)
                    .background(Color.Black)
            ) {
                if (isPreviewing) CameraPreviewPlaceholder() else PreviewPausedPlaceholder()
            }
            VerticalDivider()
            MonitoringStatusPanel(isMonitoring, modifier = Modifier
                .weight(1f)
                .fillMaxHeight())
        }
        HorizontalDivider()
        ActionButtonsHorizontal(
            isPreviewing, isMonitoring, isAlarmActive,
            onPreviewToggle, onMonitoringToggle, onResetAlarm
        )
    }
}


// --- 共享 UI 组件 ---

@Composable
private fun CameraPreviewPlaceholder() { /* ... 内容不变 ... */ }

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
                modifier = Modifier.size(96.dp), tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "预览已暂停", style = MaterialTheme.typography.headlineSmall, color = Color.LightGray)
        }
    }
}

@Composable
private fun MonitoringStatusPanel(isMonitoring: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("监控状态", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))
        Text("模式: 主机模式", style = MaterialTheme.typography.bodyMedium)
        Text("蓝牙: 已连接 1 个从机", style = MaterialTheme.typography.bodyMedium)
        Text("AI识别: ${if (isMonitoring) "已开启 - 车辆" else "未启动"}", style = MaterialTheme.typography.bodyMedium)
        Text("当前事件: ${if (isMonitoring) "检测到移动" else "无"}", style = MaterialTheme.typography.bodyMedium)
        Text("置信度: 92%", style = MaterialTheme.typography.bodyMedium)
        Text("录制状态: 未录制", style = MaterialTheme.typography.bodyMedium)
    }
}

// 按钮容器A：垂直排列 (用于宽屏)
@Composable
private fun ActionButtonsVertical(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionButton(text = if (isPreviewing) "停止预览" else "开始预览", icon = if (isPreviewing) Icons.Default.VisibilityOff else Icons.Default.Visibility, onClick = onPreviewToggle, modifier = Modifier.fillMaxWidth())
        HorizontalDivider()
        ActionButton(text = if (isMonitoring) "暂停监控" else "开始监控", icon = if (isMonitoring) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline, onClick = onMonitoringToggle, isPrimary = true, modifier = Modifier.fillMaxWidth())
        HorizontalDivider()
        ActionButton(text = "复位警报", icon = Icons.Default.Refresh, onClick = onResetAlarm, enabled = isAlarmActive, modifier = Modifier.fillMaxWidth())
    }
}

// 按钮容器B：水平排列 (用于窄屏)
@Composable
private fun ActionButtonsHorizontal(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(text = if (isPreviewing) "停止预览" else "开始预览", icon = if (isPreviewing) Icons.Default.VisibilityOff else Icons.Default.Visibility, onClick = onPreviewToggle, modifier = Modifier.weight(1f).fillMaxHeight(), isVerticalContent = false)
        VerticalDivider()
        ActionButton(text = if (isMonitoring) "暂停监控" else "开始监控", icon = if (isMonitoring) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline, onClick = onMonitoringToggle, isPrimary = true, modifier = Modifier.weight(1f).fillMaxHeight(), isVerticalContent = false)
        VerticalDivider()
        ActionButton(text = "复位警报", icon = Icons.Default.Refresh, onClick = onResetAlarm, enabled = isAlarmActive, modifier = Modifier.weight(1f).fillMaxHeight(), isVerticalContent = false)
    }
}


// 可复用的快捷按钮组件
@Composable
private fun ActionButton(
    text: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier,
    enabled: Boolean = true, isPrimary: Boolean = false,
    isVerticalContent: Boolean = true
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    } else {
        ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    }

    TextButton(
        onClick = onClick, enabled = enabled, shape = RectangleShape,
        modifier = modifier,
        colors = buttonColors,
        contentPadding = if (isVerticalContent) PaddingValues(vertical = 12.dp, horizontal = 8.dp) else PaddingValues(vertical = 4.dp, horizontal = 4.dp)
    ) {
        // 核心修复：移除 contentModifier，让内容尺寸自适应
        if (isVerticalContent) {
            // 垂直布局 (用于宽屏右侧)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            }
        } else {
            // 水平布局 (用于窄屏底部)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Normal, maxLines = 1)
            }
        }
    }
}

// 分隔线组件
@Composable
private fun VerticalDivider() {
    Box(Modifier.fillMaxHeight().width(1.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)))
}

@Composable
private fun HorizontalDivider() {
    Box(Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)))
}


// --- 预览 ---
@Preview(showBackground = true, device = "spec:width=1280dp,height=700dp,dpi=240,orientation=landscape", name = "Wide Screen Preview (>16:9)")
@Composable
fun PreviewScreenPreviewWide() {
    SentinelTheme(currentAppTheme = AppTheme.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PreviewScreen(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=720dp,dpi=240,orientation=landscape", name = "Standard Screen Preview (16:9)")
@Composable
fun PreviewScreenPreviewNarrow() {
    SentinelTheme(currentAppTheme = AppTheme.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PreviewScreen(navController = rememberNavController())
        }
    }
}
