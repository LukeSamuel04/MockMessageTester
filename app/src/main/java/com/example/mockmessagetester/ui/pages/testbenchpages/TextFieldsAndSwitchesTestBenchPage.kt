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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
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
import com.example.mockmessagetester.ui.components.switches.PrimarySwitch
import com.example.mockmessagetester.ui.components.switches.SecondarySwitch
import com.example.mockmessagetester.ui.components.textfields.PrimaryTextField
import com.example.mockmessagetester.ui.components.textfields.SecondaryTextField
import com.example.mockmessagetester.ui.theme.InboxZeroTheme

@Composable
fun TextFieldsAndSwitchesTestBenchPage() {
    // 状态管理：为了让输入框和开关在测试页上能够真正交互，我们需要暂存它们的状态
    var primaryText by remember { mutableStateOf("") }
    var secondaryText by remember { mutableStateOf("Mock Sender 01") } // 给次级输入框一个默认值

    var primarySwitchState by remember { mutableStateOf(true) }
    var secondarySwitchState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "输入与控制组件测试",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        // ==========================================
        // 1. TextFields (输入框) 测试组
        // ==========================================
        PrimaryContainer(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "TextFields (文本输入)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Primary TextField (高频主输入)
                Column {
                    Text(
                        text = "Primary (全包围边框，聚焦时变蓝)",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryTextField(
                        value = primaryText,
                        onValueChange = { primaryText = it },
                        placeholderText = "请输入回复内容..."
                    )
                }

                // Secondary TextField (低频配置输入)
                Column {
                    Text(
                        text = "Secondary (沉浸式下划线，静默隐形)",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryTextField(
                        value = secondaryText,
                        onValueChange = { secondaryText = it },
                        placeholderText = "配置项名称"
                    )
                }
            }
        }

        // ==========================================
        // 2. Switches (开关) 测试组
        // ==========================================
        SecondaryContainer(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Switches (状态切换)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Primary Switch (全局重型控制)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Primary Switch",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "带图标支持，使用主色调 (MorandiBlue)",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    PrimarySwitch(
                        checked = primarySwitchState,
                        onCheckedChange = { primarySwitchState = it },
                        // 测试动态切换图标：根据状态显示太阳或月亮
                        thumbIcon = if (primarySwitchState) Icons.Filled.LightMode else Icons.Filled.DarkMode
                    )
                }

                // Secondary Switch (局部高频控制)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Secondary Switch",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "纯粹胶囊，开启为安全运行色 (MorandiGreen)",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    SecondarySwitch(
                        checked = secondarySwitchState,
                        onCheckedChange = { secondarySwitchState = it }
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
fun TextFieldsAndSwitchesPreviewLight() {
    InboxZeroTheme(darkTheme = false) {
        TextFieldsAndSwitchesTestBenchPage()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun TextFieldsAndSwitchesPreviewDark() {
    InboxZeroTheme(darkTheme = true) {
        TextFieldsAndSwitchesTestBenchPage()
    }
}