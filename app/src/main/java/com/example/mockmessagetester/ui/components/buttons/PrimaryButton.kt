//重要度1按钮
package com.example.mockmessagetester.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * InboxZero 极简主按钮 (Primary Atom)
 *
 * 核心设计规则 (HCI)：
 * 1. 彻底拍平：去除所有系统默认的物理阴影 (Elevation = 0)，消除厚重感。
 * 2. 柔和边界：使用 12dp 的柔和圆角，而非尖锐直角或过度活泼的胶囊状。
 * 3. 颜色托管：强制绑定 Theme 引擎的 primary 颜色（莫兰迪蓝），杜绝硬编码。
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        // 1. 彻底拍平阴影
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        // 2. 柔和圆角
        shape = RoundedCornerShape(12.dp),
        // 3. 颜色绑定（自动适配深浅色模式的 MorandiBlue）
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            // 禁用状态下的克制表现
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        ),
        // 适当扩大点击区域，增加内边距，提升手指点击的工效学体验
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        // 文字样式直接读取我们在 Type.kt 中定义好的 labelLarge
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}