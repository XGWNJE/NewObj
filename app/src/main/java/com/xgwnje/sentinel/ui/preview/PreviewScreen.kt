package com.xgwnje.sentinel.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.* // Keep for FloatingActionButton, Icon, Text etc.
import androidx.compose.runtime.Composable
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

// @OptIn(ExperimentalMaterial3Api::class) // No longer strictly needed if Scaffold is removed or simplified

@Composable
fun PreviewScreen(
    @Suppress("UNUSED_PARAMETER") navController: NavController,
    modifier: Modifier = Modifier
) {
    // No Scaffold, directly use a Box or other layout for the screen content
    Box(
        modifier = modifier // Use the modifier passed to the screen
            .fillMaxSize()
            // No innerPadding from Scaffold needed anymore.
            // The .windowInsetsPadding(WindowInsets.safeDrawing) in SentinelApp.kt
            // already handles system bar insets for the whole content area.
            .background(Color.DarkGray), // Simulate camera preview area background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.VideocamOff,
                contentDescription = stringResource(R.string.preview_camera_placeholder),
                modifier = Modifier.size(128.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.preview_camera_placeholder),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.preview_status_stopped_placeholder),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )
        }

        FloatingActionButton(
            onClick = { /* TODO: Implement Start/Stop Monitoring */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp) // Keep padding for FAB positioning relative to screen edges
        ) {
            Text(stringResource(id = R.string.preview_start_monitoring_button))
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun PreviewScreenPreview() {
    SentinelTheme {
        PreviewScreen(navController = rememberNavController())
    }
}