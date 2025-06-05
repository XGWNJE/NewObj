package com.xgwnje.sentinel.ui.settings.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xgwnje.sentinel.R
import com.xgwnje.sentinel.data.AppTheme
import com.xgwnje.sentinel.data.ThemeDataStore
import kotlinx.coroutines.launch

@Composable
fun GeneralSettingsSection(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    // In a real app with DI, you'd inject ThemeDataStore or a ViewModel using it.
    // For simplicity here, we create it directly.
    val themeDataStore = rememberCoroutineScope().run { ThemeDataStore(context) } // Create instance tied to a scope
    val currentAppTheme by themeDataStore.appThemeFlow.collectAsState(initial = AppTheme.DARK)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxWidth()) { // Removed outer padding, can be added by parent if needed
        Text(
            text = stringResource(id = R.string.general_settings_theme_title),
            style = MaterialTheme.typography.titleLarge, // Made title larger for section
            modifier = Modifier.padding(bottom = 12.dp)
        )

        AppTheme.entries.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        coroutineScope.launch {
                            themeDataStore.setAppTheme(theme)
                        }
                    }
                    .padding(vertical = 12.dp) // Increased padding for better touch target
            ) {
                RadioButton(
                    selected = (theme == currentAppTheme),
                    onClick = { // RadioButton's onClick can also trigger the change
                        coroutineScope.launch {
                            themeDataStore.setAppTheme(theme)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(16.dp)) // Increased spacer
                Text(
                    text = when (theme) {
                        AppTheme.LIGHT -> stringResource(id = R.string.theme_light)
                        AppTheme.DARK -> stringResource(id = R.string.theme_dark)
                        // Handle AppTheme.SYSTEM here if you add it
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}