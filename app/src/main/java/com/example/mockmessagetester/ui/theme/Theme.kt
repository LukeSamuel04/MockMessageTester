//主题引擎：根据系统自动切换深浅色
package com.example.mockmessagetester.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 1. 深色模式配色方案映射
private val DarkColorScheme = darkColorScheme(
    primary = MorandiBlueDark,
    secondary = MorandiGreen, // 用于成功/运行状态
    tertiary = MorandiRed,    // 用于警告/停止状态
    background = BackgroundDark,
    surface = SurfaceDark,

    onPrimary = TextPrimaryDark,     // 主按钮上的文字颜色
    onBackground = TextPrimaryDark,  // 背景上的文字颜色
    onSurface = TextPrimaryDark      // 卡片上的文字颜色
)

// 2. 浅色模式配色方案映射
private val LightColorScheme = lightColorScheme(
    primary = MorandiBlue,
    secondary = MorandiGreen,
    tertiary = MorandiRed,
    background = BackgroundLight,
    surface = SurfaceLight,

    onPrimary = SurfaceLight,        // 浅色模式下，蓝色主按钮上用白色文字
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight
)

/**
 * InboxZero 主题引擎
 * 负责根据系统设置，自动将底层的颜色、字体注入到整个应用中。
 */
@Composable
fun InboxZeroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 动态选择色板
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // 处理系统状态栏 (沉浸式防焦虑设计：让状态栏颜色和背景色融为一体)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            // 浅色模式下状态栏图标变黑，深色模式下变白
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // 组装并提供给 Material 3 引擎
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}