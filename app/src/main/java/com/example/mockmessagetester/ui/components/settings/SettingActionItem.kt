package com.example.mockmessagetester.ui.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * 动作型设置项语法糖。
 * 右侧不放置组件，通常用于单纯的点击操作（如清理缓存、退出登录）。
 */
@Composable
fun SettingActionItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    titleColor: Color = MaterialTheme.colorScheme.onSurface // 默认是普通颜色，可传入 Error 色
) {
    SettingItemBase(
        title = title,
        subtitle = subtitle,
        modifier = modifier,
        leadingIcon = leadingIcon,
        onClick = onClick,
        titleColor = titleColor
    )
}