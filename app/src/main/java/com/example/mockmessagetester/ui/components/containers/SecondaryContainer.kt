//重要度2容器
package com.example.mockmessagetester.ui.components.containers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * InboxZero 次级容器 (Secondary Container)
 *
 * 核心设计规则 (HCI)：
 * 1. 结构降级：用于包裹次要信息（如：日志记录、辅助说明文本等），不抢占视觉焦点。
 * 2. 轮廓界定：不使用背景填充色，而是使用透明背景配合极细的柔和边框，划定势力范围。
 * 3. 内部呼吸：与主容器保持完全一致的内边距，确保全局排版节奏统一。
 */
@Composable
fun SecondaryContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp), // 保持和主容器一致的圆角
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent, // 背景完全透明，融入底层
            contentColor = MaterialTheme.colorScheme.onSurface // 内部文字默认使用标准表面文字色
        ),
        border = BorderStroke(
            width = 1.dp,
            // 使用比 Primary 更加低调的 onSurfaceVariant（浅灰色），并带点透明度
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
        )
    ) {
        // 依然保持 20dp 的统一呼吸感
        Box(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}