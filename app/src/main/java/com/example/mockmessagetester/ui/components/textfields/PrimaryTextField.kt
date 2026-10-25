//重要度1输入框
package com.example.mockmessagetester.ui.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * InboxZero 主输入框 (Primary TextField)
 *
 * 核心设计规则 (HCI)：
 * 1. 明确的交互边界：作为高频使用的核心输入入口（如：回复消息），提供完整的圆角矩形边框。
 * 2. 降噪背景：彻底摒弃 Material 默认的深色填充底色，使用纯透明背景，让输入框完美融入底层的 Container。
 * 3. 动态视觉聚焦：静默状态下使用柔和的浅灰边框；只有当用户点击获得焦点时，边框才变为主色调 (MorandiBlue)，引导注意力。
 */
@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(), // 默认占据整行宽度
        enabled = enabled,
        singleLine = singleLine,
        // 1. 统一圆角：保持与容器/按钮一致或略小的 12dp 圆角，维持圆润无攻击性的整体感
        shape = RoundedCornerShape(12.dp),
        // 文字排版：使用正文样式
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface // 确保输入文字的颜色正确
        ),
        // 占位符：用户未输入时的提示语
        placeholder = {
            if (placeholderText.isNotEmpty()) {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) // 占位符使用更淡的颜色
                )
            }
        },
        // 2 & 3. 极致克制的颜色定制
        colors = OutlinedTextFieldDefaults.colors(
            // --- 背景与游标 ---
            focusedContainerColor = Color.Transparent,   // 坚决不用底色
            unfocusedContainerColor = Color.Transparent, // 坚决不用底色
            disabledContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary, // 闪烁的光标使用温和的莫兰迪蓝

            // --- 边框颜色动态变化 ---
            focusedBorderColor = MaterialTheme.colorScheme.primary, // 选中时，边框变为莫兰迪蓝
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), // 未选中时，呈现极淡的灰色边框
            disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),

            // --- 文字颜色 ---
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    )
}