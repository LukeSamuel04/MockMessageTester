package com.example.mockmessagetester.ui.pages.testbenchpages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.mockmessagetester.ui.components.containers.PrimaryContainer
import com.example.mockmessagetester.ui.components.containers.SecondaryContainer
import com.example.mockmessagetester.ui.components.dropdowns.PrimaryDropdown
import com.example.mockmessagetester.ui.components.dropdowns.SecondaryDropdown
import com.example.mockmessagetester.ui.theme.InboxZeroTheme

@Composable
fun DropdownTestBenchPage() {
    // 状态管理：定义下拉框的选项列表和当前选中的状态
    val primaryOptions = listOf("单发模式 (Manual)", "连发模式 (Auto-Send)", "剧本模式 (Scripted)")
    var selectedPrimaryOption by remember { mutableStateOf(primaryOptions[0]) }

    val secondaryOptions = listOf("500 ms", "1.0 s", "2.0 s", "随机")
    var selectedSecondaryOption by remember { mutableStateOf(secondaryOptions[1]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "下拉选择框组件测试",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        // ==========================================
        // 1. Primary Dropdown (主下拉框) 测试
        // ==========================================
        PrimaryContainer(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Primary Dropdown (总闸级)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "拥有完整边界，适合用于控制台顶部的模式切换。",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                PrimaryDropdown(
                    options = primaryOptions,
                    selectedOption = selectedPrimaryOption,
                    onOptionSelected = { selectedPrimaryOption = it },
                    label = "当前发送模式"
                )
            }
        }

        // ==========================================
        // 2. Secondary Dropdown (次级下拉框) 测试
        // ==========================================
        SecondaryContainer(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Secondary Dropdown (参数级)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "极致隐身，无边框，适合嵌在句子中作为细微参数调整。",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 模拟一个连发模式的参数设置场景
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "每隔",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    SecondaryDropdown(
                        options = secondaryOptions,
                        selectedOption = selectedSecondaryOption,
                        onOptionSelected = { selectedSecondaryOption = it },
                        modifier = Modifier.padding(horizontal = 4.dp) // 给下拉框两边留点呼吸空间
                    )

                    Text(
                        text = "发送一条模拟消息",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// ==========================================
// IDE 本地热预览
// ==========================================
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun DropdownPreviewLight() {
    InboxZeroTheme(darkTheme = false) {
        DropdownTestBenchPage()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun DropdownPreviewDark() {
    InboxZeroTheme(darkTheme = true) {
        DropdownTestBenchPage()
    }
}