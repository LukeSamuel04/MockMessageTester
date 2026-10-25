package com.example.mockmessagetester.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

// 导入你自己造好的精密零件
import com.example.mockmessagetester.ui.components.switches.PrimarySwitch
import com.example.mockmessagetester.ui.components.switches.SecondarySwitch

/**
 * 开关型设置项语法糖。
 * 默认使用低调降噪的 SecondarySwitch，支持一键切换为带图标的全局 PrimarySwitch。
 * 并且支持点击整行进行切换，扩大触控热区。
 */
@Composable
fun SettingSwitchItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,

    // --- 新增：工业级灵活调度配置 ---
    usePrimarySwitch: Boolean = false, // 默认使用代表日常模块的绿底次级开关
    thumbIcon: ImageVector? = null     // 专供 PrimarySwitch 渲染滑块内部的图标（如深浅色月亮图标）
) {
    SettingItemBase(
        title = title,
        subtitle = subtitle,
        modifier = modifier,
        leadingIcon = leadingIcon,
        // 核心防呆设计：如果处于启用状态，点击整行也能触发开关切换
        onClick = if (enabled) { { onCheckedChange(!isChecked) } } else null,
        trailingContent = {
            // 根据参数动态挂载你封装的不同级别的 Switch
            if (usePrimarySwitch) {
                PrimarySwitch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier,
                    thumbIcon = thumbIcon,
                    enabled = enabled
                )
            } else {
                SecondarySwitch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier,
                    enabled = enabled
                )
            }
        }
    )
}