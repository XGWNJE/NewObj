package com.xgwnje.sentinel.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance scoped to the application context
// The name "settings_theme" will be the filename for the Preferences DataStore.
val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_theme")

class ThemeDataStore(private val context: Context) {
    companion object {
        // Define the key for storing the theme preference as a String
        val APP_THEME_KEY = stringPreferencesKey("app_theme")
    }

    // Flow to observe theme changes
    val appThemeFlow: Flow<AppTheme> = context.themeDataStore.data
        .map { preferences ->
            // Read the theme name string, default to DARK if not found
            val themeName = preferences[APP_THEME_KEY] ?: AppTheme.DARK.name
            try {
                // Convert the stored string name back to the AppTheme enum
                AppTheme.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                // If the stored value is somehow invalid, fallback to DARK
                AppTheme.DARK
            }
        }

    // Function to update and persist the chosen theme
    suspend fun setAppTheme(theme: AppTheme) {
        context.themeDataStore.edit { settings ->
            settings[APP_THEME_KEY] = theme.name
        }
    }
}