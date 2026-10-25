package com.example.mockmessagetester.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 导入我们定义好的次级容器
import com.example.mockmessagetester.ui.components.containers.SecondaryContainer

/**
 * 工业级设置分组卡片 (Container)
 * 负责渲染左上角的分组标题，并使用统一的 SecondaryContainer 承载具体设置项。
 */
@Composable
fun SettingSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // 分组小标题
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        // 完美复用全站样式的底层容器：提供透明底色和极细边框
        SecondaryContainer(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 桥接层：因为 SecondaryContainer 内部是 Box (堆叠排版)，
            // 而设置项需要从上到下排列，所以在此处显式插入一个 Column
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}