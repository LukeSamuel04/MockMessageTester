//重要度2输入框
package com.example.mockmessagetester.ui.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * InboxZero 次级输入框 (Secondary TextField)
 *
 * 核心设计规则 (HCI)：
 * 1. 极低视觉权重：用于配置/设置类低频操作（如：配置 Mock Sender 名称）。抛弃全包围边框，仅保留底部的轻微指示线。
 * 2. 绝对背景透明：彻底融入底层容器，不产生任何“块状”割裂感。
 * 3. 弱化存在感：未聚焦时几乎如同普通文本；聚焦时也不使用满血高光，而是使用带透明度的主色调。
 */
@Composable
fun SecondaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true // 次级配置项通常是单行输入
) {
    // 放弃 OutlinedTextField，改用基础的 TextField 来实现下划线风格
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = singleLine,
        // 文字排版：保持正文样式的一致性
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        placeholder = {
            if (placeholderText.isNotEmpty()) {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodyLarge,
                    // 占位符颜色比 PrimaryTextField 更淡一点
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        },
        // 极致降级的颜色定制
        colors = TextFieldDefaults.colors(
            // --- 彻底干掉背景色 ---
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,

            // --- 游标颜色 ---
            cursorColor = MaterialTheme.colorScheme.primary,

            // --- 底部指示线 (Underline) 极简化 ---
            // 聚焦时：使用 50% 透明度的主色调，避免喧宾夺主
            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            // 未聚焦时：使用仅有 15% 透明度的极淡灰色，平时几乎隐形
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f),
            // 禁用时：直接隐藏线条
            disabledIndicatorColor = Color.Transparent,

            // --- 文本颜色 ---
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    )
}