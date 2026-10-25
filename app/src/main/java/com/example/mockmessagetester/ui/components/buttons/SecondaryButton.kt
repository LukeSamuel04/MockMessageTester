//重要度2按钮
package com.example.mockmessagetester.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * InboxZero 次级柔和按钮 (Secondary Atom)
 *
 * 核心设计规则 (HCI)：
 * 1. 降低视觉权重：作为辅助操作按钮，不使用高饱和度填充色，避免喧宾夺主。
 * 2. 轮廓暗示：使用极细的极简描边 (Outlined) 或极淡的背景，给予点击暗示，但保持克制。
 * 3. 色彩关联：文字和边框颜色与主色调 (Primary) 保持一致，维持视觉整体性。
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // 使用 Compose 官方提供的 OutlinedButton 作为基础来实现次级按钮
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        // 设置极细的柔和边框，使用带透明度的主色调，避免生硬
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        ),
        // 彻底抛弃背景填充色，让底层容器的颜色透过来
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary, // 文字保持主色，与边框呼应
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}