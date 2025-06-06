package com.xgwnje.sentinel.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xgwnje.sentinel.R
import com.xgwnje.sentinel.camera.CameraPreview

@Composable
internal fun LandscapeWideLayout(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean, currentEvent: String,
    viewModel: PreviewViewModel,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(4 / 3f, matchHeightConstraintsFirst = true)
                .background(Color.Black)
        ) {
            // 核心修复：CameraPreview 始终在组合中
            CameraPreview(viewModel = viewModel, isPreviewing = isPreviewing)

            // 如果不预览，则在上方叠加占位符
            if (!isPreviewing) {
                PreviewPausedPlaceholder()
            }
        }
        VerticalDivider()
        Column(modifier = Modifier.fillMaxSize()) {
            MonitoringStatusPanel(isMonitoring, currentEvent, modifier = Modifier.weight(1f))
            HorizontalDivider()
            ActionButtonsVertical(
                isPreviewing, isMonitoring, isAlarmActive,
                onPreviewToggle, onMonitoringToggle, onResetAlarm
            )
        }
    }
}

@Composable
internal fun LandscapeNarrowLayout(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean, currentEvent: String,
    viewModel: PreviewViewModel,
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
                // 核心修复：CameraPreview 始终在组合中
                CameraPreview(viewModel = viewModel, isPreviewing = isPreviewing)

                // 如果不预览，则在上方叠加占位符
                if (!isPreviewing) {
                    PreviewPausedPlaceholder()
                }
            }
            VerticalDivider()
            MonitoringStatusPanel(isMonitoring, currentEvent, modifier = Modifier.weight(1f).fillMaxHeight())
        }
        HorizontalDivider()
        ActionButtonsHorizontal(
            isPreviewing, isMonitoring, isAlarmActive,
            onPreviewToggle, onMonitoringToggle, onResetAlarm
        )
    }
}

@Composable
internal fun PreviewPausedPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(Color.DarkGray),
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
internal fun MonitoringStatusPanel(isMonitoring: Boolean, currentEvent: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("监控状态", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))
        Text("监控中: ${if (isMonitoring) "是" else "否"}", style = MaterialTheme.typography.bodyMedium)
        Text("当前事件: $currentEvent", style = MaterialTheme.typography.bodyMedium, color = if (currentEvent != "无") MaterialTheme.colorScheme.error else Color.Unspecified)
    }
}

@Composable
internal fun ActionButtonsVertical(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        ActionButton(text = if (isPreviewing) "停止预览" else "开始预览", icon = if (isPreviewing) Icons.Default.VisibilityOff else Icons.Default.Visibility, onClick = onPreviewToggle, modifier = Modifier.fillMaxWidth())
        HorizontalDivider()
        ActionButton(text = if (isMonitoring) "暂停监控" else "开始监控", icon = if (isMonitoring) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline, onClick = onMonitoringToggle, isPrimary = true, modifier = Modifier.fillMaxWidth())
        HorizontalDivider()
        ActionButton(text = "复位警报", icon = Icons.Default.Refresh, onClick = onResetAlarm, enabled = isAlarmActive, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
internal fun ActionButtonsHorizontal(
    isPreviewing: Boolean, isMonitoring: Boolean, isAlarmActive: Boolean,
    onPreviewToggle: () -> Unit, onMonitoringToggle: () -> Unit, onResetAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        ActionButton(text = if (isPreviewing) "停止预览" else "开始预览", icon = if (isPreviewing) Icons.Default.VisibilityOff else Icons.Default.Visibility, onClick = onPreviewToggle, modifier = Modifier.weight(1f).fillMaxHeight(), isVerticalContent = false)
        VerticalDivider()
        ActionButton(text = if (isMonitoring) "暂停监控" else "开始监控", icon = if (isMonitoring) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline, onClick = onMonitoringToggle, isPrimary = true, modifier = Modifier.weight(1f).fillMaxHeight(), isVerticalContent = false)
        VerticalDivider()
        ActionButton(text = "复位警报", icon = Icons.Default.Refresh, onClick = onResetAlarm, enabled = isAlarmActive, modifier = Modifier.weight(1f).fillMaxHeight(), isVerticalContent = false)
    }
}

@Composable
internal fun ActionButton(
    text: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier,
    enabled: Boolean = true, isPrimary: Boolean = false, isVerticalContent: Boolean = true
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.textButtonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary, disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
    } else {
        ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
    }

    TextButton(
        onClick = onClick, enabled = enabled, shape = RectangleShape,
        modifier = modifier, colors = buttonColors,
        contentPadding = if (isVerticalContent) PaddingValues(vertical = 12.dp, horizontal = 8.dp) else PaddingValues(vertical = 4.dp, horizontal = 4.dp)
    ) {
        if (isVerticalContent) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Normal, maxLines = 1)
            }
        }
    }
}

@Composable
internal fun VerticalDivider() { Box(Modifier.fillMaxHeight().width(1.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))) }
@Composable
internal fun HorizontalDivider() { Box(Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))) }

@Composable
internal fun PermissionRequestScreen(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("需要摄像头权限", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("为了使用实时预览和监控功能，请授予摄像头权限。")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestPermission) { Text("授予权限") }
    }
}
