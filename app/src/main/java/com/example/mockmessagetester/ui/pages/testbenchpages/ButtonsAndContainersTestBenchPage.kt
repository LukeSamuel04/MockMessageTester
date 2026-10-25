//测试界面
package com.example.mockmessagetester.ui.pages.testbenchpages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 导入我们自己手搓的组件
import com.example.mockmessagetester.ui.components.buttons.PrimaryButton
import com.example.mockmessagetester.ui.components.buttons.SecondaryButton
import com.example.mockmessagetester.ui.components.buttons.TextButton
import com.example.mockmessagetester.ui.components.containers.GhostContainer
import com.example.mockmessagetester.ui.components.containers.PrimaryContainer
import com.example.mockmessagetester.ui.components.containers.SecondaryContainer
import com.example.mockmessagetester.ui.theme.InboxZeroTheme

/**
 * InboxZero 组件测试台 (Test Bench)
 * 用于独立测试和预览所有基础原子组件的视觉一致性
 */
@Composable
fun TestBenchPage() {
    // 外层使用 Column (垂直列) 充当“货架”
    Column(
        modifier = Modifier
            .fillMaxSize() // 占满整个屏幕
            .background(MaterialTheme.colorScheme.background) // 强制铺上防焦虑极简背景色
            .verticalScroll(rememberScrollState()) // 允许上下滚动，防止组件溢出屏幕
            .padding(24.dp), // 屏幕整体大边距
        verticalArrangement = Arrangement.spacedBy(24.dp) // 组件之间自动空开 24dp 的安全距离
    ) {
        // 页面大标题
        Text(
            text = "HCI 极简组件测试台",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        // ==========================================
        // 1. 高权重核心组件展示
        // ==========================================
        PrimaryContainer(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "Primary Container (核心容器)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "纯白无阴影，负责包裹当前页面最重要的模拟控制台。下面展示了高优先级的动作按钮。",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(
                    text = "Primary Action (主操作)",
                    onClick = { /* 测试点击，目前为空 */ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // ==========================================
        // 2. 中等权重次级组件展示
        // ==========================================
        SecondaryContainer(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "Secondary Container (次级容器)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "背景全透，极细边框。用于包裹不那么紧要的模块（如日志列表）。",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SecondaryButton(
                    text = "Secondary Action (次级操作)",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // ==========================================
        // 3. 最低权重隐形组件展示
        // ==========================================
        GhostContainer(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "Ghost Container (幽灵容器)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "绝对隐形，仅用于提供 20dp 的内边距约束，确保与其他卡片完美对齐。",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    text = "Text Action (纯文字操作，如：清空日志)",
                    onClick = { }
                )
            }
        }
    }
}

// ==========================================
// IDE 本地热预览 (无需跑在手机上也能看)
// ==========================================
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TestBenchPreviewLight() {
    // 记得必须包一层 Theme (发射塔)，否则组件收不到颜色信号
    InboxZeroTheme(darkTheme = false) {
        TestBenchPage()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun TestBenchPreviewDark() {
    InboxZeroTheme(darkTheme = true) {
        TestBenchPage()
    }
}