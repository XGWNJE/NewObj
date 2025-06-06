package com.xgwnje.sentinel.ui.preview

import android.Manifest
import android.app.Application // 核心修复：添加 import
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext // 核心修复：添加 import
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    @Suppress("UNUSED_PARAMETER") navController: NavController,
    modifier: Modifier = Modifier,
    // 核心修复：直接使用 viewModel()，让库来处理 AndroidViewModel 的创建
    viewModel: PreviewViewModel
) {
    val isMonitoring by viewModel.isMonitoring.collectAsState()
    val isPreviewing by viewModel.isPreviewing.collectAsState()
    val isAlarmActive by viewModel.isAlarmActive.collectAsState()
    val currentEvent by viewModel.currentEvent.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding -> // 确保 innerPadding 被使用

        val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

        Box(modifier = Modifier.padding(innerPadding)) { // 使用 innerPadding
            if (cameraPermissionState.status.isGranted) {
                val configuration = LocalConfiguration.current
                val screenAspectRatio = configuration.screenWidthDp.toFloat() / configuration.screenHeightDp.toFloat()
                val useWideLayout = screenAspectRatio > 1.8f

                if (useWideLayout) {
                    LandscapeWideLayout(
                        isPreviewing, isMonitoring, isAlarmActive, currentEvent, viewModel,
                        viewModel::onPreviewToggle, viewModel::onMonitoringToggle, viewModel::onResetAlarm
                    )
                } else {
                    LandscapeNarrowLayout(
                        isPreviewing, isMonitoring, isAlarmActive, currentEvent, viewModel,
                        viewModel::onPreviewToggle, viewModel::onMonitoringToggle, viewModel::onResetAlarm
                    )
                }
            } else {
                PermissionRequestScreen(
                    onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
                )
            }
        }
    }
}
