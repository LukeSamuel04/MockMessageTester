//重要度1容器
package com.example.mockmessagetester.ui.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * InboxZero 核心容器 (Primary Container)
 *
 * 核心设计规则 (HCI)：
 * 1. 结构基石：用于包裹页面中最重要的功能模块（如：模拟发送控制台主体）。
 * 2. 视觉浮出：通过纯净的 Surface 颜色（纯白或深色模式下的微亮深灰）与背景产生极弱的层次差。
 * 3. 彻底拍平：绝不使用物理阴影 (elevation = 0)，依赖色彩和圆角来界定边缘，最大程度降低认知负荷。
 */
@Composable
fun PrimaryContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp), // 容器的圆角应该比按钮 (12dp) 稍大一点，显得更加包裹
        colors = CardDefaults.cardColors(
            // 绑定主题的 Surface 颜色，与底层的 Background 产生微妙色差
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp // 拍平，拒绝阴影干扰
        )
    ) {
        // 使用 Box 并在内部设置统一的 Padding，让装在里面的组件拥有呼吸感
        Box(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}