//重要度3无边框按钮
package com.example.mockmessagetester.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton as MaterialTextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * InboxZero 极简文字按钮 (Text Atom)
 *
 * 核心设计规则 (HCI)：
 * 1. 最低视觉权重：无边框、无背景，完全隐匿于背景中。
 * 2. 交互反馈：仅在手指按下时，通过底层默认的涟漪效果提供交互确认。
 * 3. 适用场景：用于“取消”、“清空”、“跳过”等非关键且不鼓励频繁点击的操作。
 */
@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // 使用 Compose 官方的 TextButton，并通过别名 MaterialTextButton 避免与我们自己的函数名冲突
    MaterialTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp), // 保持与其他按钮一致的点击热区形状
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant, // 使用次要文字颜色，比 primary 更低调
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp) // 文字按钮的内边距可以稍微紧凑一点
    ) {
        Text(
            text = text,
            // 依然保持字体的一致性
            style = MaterialTheme.typography.labelLarge
        )
    }
}