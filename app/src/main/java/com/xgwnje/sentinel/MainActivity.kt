package com.xgwnje.sentinel

import android.content.res.Configuration // 👈 **导入 Configuration 类**
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// import androidx.compose.foundation.isSystemInDarkTheme // 如果不使用“跟随系统”主题，则不是必需的
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.xgwnje.sentinel.data.AppTheme
import com.xgwnje.sentinel.data.ThemeDataStore // 确保这是您 ThemeDataStore 的正确路径
import com.xgwnje.sentinel.ui.theme.SentinelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // 在 super.onCreate() 之前调用
        super.onCreate(savedInstanceState)

        // ThemeDataStore 实例
        val themeDataStore = ThemeDataStore(applicationContext) // 使用 applicationContext

        // 👇 **使用兼容的方式检查夜间模式**
        // 通过检查 resources.configuration.uiMode 与 Configuration.UI_MODE_NIGHT_MASK 的位运算结果
        // 来判断当前是否为夜间模式。这种方法兼容旧版 API。
        val isNightMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ) { isNightMode }, // 👈 **使用上面检查的结果**
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ) { isNightMode } // 👈 **同样使用检查的结果**
        )

        // 获取 WindowInsetsControllerCompat 实例以控制系统栏
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        // 隐藏状态栏
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        // 设置系统栏的行为，允许通过滑动临时显示状态栏
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            // 从 DataStore 收集主题偏好，如果未存储任何内容，则默认为 DARK
            val currentAppTheme by themeDataStore.appThemeFlow.collectAsState(initial = AppTheme.DARK)

            SentinelTheme(currentAppTheme = currentAppTheme) { // 传递观察到的主题
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SentinelApp()
                }
            }
        }
    }
}
