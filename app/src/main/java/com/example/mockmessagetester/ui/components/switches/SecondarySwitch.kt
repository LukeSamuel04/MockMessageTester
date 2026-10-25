//重要度2切换器
package com.example.mockmessagetester.ui.components.switches

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * InboxZero 次级开关 (Secondary Switch)
 *
 * 核心设计规则 (HCI)：
 * 1. 业务逻辑分离：专用于表示具体功能模块的开启/关闭（如：自动连发、拦截模式等高频小模块）。
 * 2. 安全色彩暗示：开启状态使用莫兰迪绿 (Secondary Color) 代替耀眼的蓝色，传达“系统正在平稳运行”的心理暗示，降低焦虑感。
 * 3. 极简降噪：不接受任何 Icon 图标，保持最纯粹的几何胶囊形状。
 */
@Composable
fun SecondarySwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        // 次级开关剥离了图标功能，强制为 null
        thumbContent = null,
        colors = SwitchDefaults.colors(
            // --- 开启状态 (Checked) ---
            checkedThumbColor = MaterialTheme.colorScheme.surface,
            // 【核心区别】：轨道使用表示成功/运行的次要色 (通常是莫兰迪绿)
            checkedTrackColor = MaterialTheme.colorScheme.secondary,
            checkedBorderColor = Color.Transparent,

            // --- 关闭状态 (Unchecked) 依然保持优雅的灰底胶囊 ---
            uncheckedThumbColor = MaterialTheme.colorScheme.surface,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            uncheckedBorderColor = Color.Transparent,

            // --- 禁用状态 (Disabled) ---
            disabledCheckedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f),
            disabledUncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
            disabledCheckedThumbColor = MaterialTheme.colorScheme.surface,
            disabledUncheckedThumbColor = MaterialTheme.colorScheme.surface
        )
    )
}