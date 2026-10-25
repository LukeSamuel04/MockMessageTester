package com.example.mockmessagetester.ui.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * InboxZero 幽灵容器 (Ghost Container)
 *
 * 核心设计规则 (HCI)：
 * 1. 绝对隐形：无背景色，无边框，无阴影。完全融入页面底层。
 * 2. 结构约束：唯一的作用是提供与其他容器完全一致的内边距 (20dp)，
 *    保证在使用纯文字或极简组件时，页面上所有的元素边缘都能完美对齐。
 * 3. 性能极简：不使用任何 Card 组件，直接使用基础 Box，实现零额外渲染开销。
 */
@Composable
fun GhostContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    // 抛弃所有的 UI 装饰，仅仅保留一个带有标准 Padding 的空间
    Box(
        modifier = modifier.padding(20.dp),
        content = content
    )
}