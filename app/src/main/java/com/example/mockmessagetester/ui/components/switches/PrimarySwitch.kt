//重要度1切换器
package com.example.mockmessagetester.ui.components.switches

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * InboxZero 主开关 (Primary Switch)
 *
 * 核心设计规则 (HCI)：
 * 1. 扁平化 iOS 风格：去除 Material 3 默认的未选中状态边框，保持轨道的纯粹胶囊形态。
 * 2. 视觉显性 (全局级权重)：使用饱和度较高的主色调 (Primary) 表示开启，并且支持传入图标以强化全局功能的辨识度（如主题切换）。
 * 3. 极简防焦虑：去除默认的阴影干扰，仅通过干净的颜色切换来表达状态。
 */
@Composable
fun PrimarySwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbIcon: ImageVector? = null, // 支持主开关特有的图标提示（如深浅色模式图标）
    enabled: Boolean = true
) {
    // Material 3 的 Switch 可以通过极其精细的颜色定制来模拟 iOS 的纯净感
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        // 如果传入了图标，就把它嵌在那个圆形的滑块(Thumb)里面
        thumbContent = if (thumbIcon != null) {
            {
                Icon(
                    imageVector = thumbIcon,
                    contentDescription = null,
                    // 图标颜色根据选中状态自动反转，确保对比度可见
                    tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else null,
        colors = SwitchDefaults.colors(
            // --- 开启状态 (Checked) ---
            checkedThumbColor = MaterialTheme.colorScheme.surface, // 开启时滑块为纯净的表面色（通常是纯白或纯深灰）
            checkedTrackColor = MaterialTheme.colorScheme.primary, // 轨道变为莫兰迪蓝
            checkedBorderColor = Color.Transparent, // 彻底去除轨道边框

            // --- 关闭状态 (Unchecked) - [打造 iOS 风格的核心] ---
            uncheckedThumbColor = MaterialTheme.colorScheme.surface, // 关闭时滑块依然是纯洁无瑕的表面色
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant, // 轨道变成低调平整的灰底
            uncheckedBorderColor = Color.Transparent, // [关键防呆] 原生 M3 会在这里加一圈巨丑的灰色边框，必须强制透明化！

            // --- 禁用状态 (Disabled) 维持克制 ---
            disabledCheckedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
            disabledUncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
            disabledCheckedThumbColor = MaterialTheme.colorScheme.surface,
            disabledUncheckedThumbColor = MaterialTheme.colorScheme.surface
        )
    )
}