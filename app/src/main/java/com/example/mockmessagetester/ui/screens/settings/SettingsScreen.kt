package com.example.mockmessagetester.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mockmessagetester.ui.components.settings.SettingActionItem
import com.example.mockmessagetester.ui.components.settings.SettingSectionCard
import com.example.mockmessagetester.ui.components.settings.SettingSwitchItem

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel, // 核心修改：强制要求传入 ViewModel
    modifier: Modifier = Modifier
) {
    // 核心修改：通过 collectAsState 订阅底层的真实数据流 (StateFlow)
    // 从此 UI 变成了一个纯粹的展示板，一切状态听指挥
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ==========================================
        // 1. 外观与交互
        // ==========================================
        SettingSectionCard(title = "外观与交互") {
            SettingSwitchItem(
                title = "深色模式",
                subtitle = "强制开启深色主题界面",
                isChecked = uiState.isDarkMode, // 从 uiState 读值
                onCheckedChange = { viewModel.updateDarkMode(it) }, // 发送指令给 viewModel，绝不自己瞎改
                usePrimarySwitch = true,
                thumbIcon = if (uiState.isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode
            )
            SettingSwitchItem(
                title = "全局触觉反馈",
                subtitle = "转动表盘或点击按钮时的马达震动",
                isChecked = uiState.isHapticEnabled,
                onCheckedChange = { viewModel.updateHaptic(it) }
            )
        }

        // ==========================================
        // 2. 测试引擎偏好
        // ==========================================
        SettingSectionCard(title = "测试引擎偏好") {
            SettingSwitchItem(
                title = "附加相对时间戳",
                subtitle = "在连发消息尾部追加 @xxx_ms",
                isChecked = uiState.appendTimestamp,
                onCheckedChange = { viewModel.updateTimestamp(it) }
            )
            SettingSwitchItem(
                title = "自动折叠通知",
                subtitle = "模拟微信的多条消息合并效果",
                isChecked = uiState.autoGroup,
                onCheckedChange = { viewModel.updateAutoGroup(it) }
            )
        }

        // ==========================================
        // 3. 系统操作 (危险区)
        // ==========================================
        SettingSectionCard(title = "系统操作") {
            SettingActionItem(
                title = "一键清空状态栏通知",
                subtitle = "清除由本应用发出的所有测试通知",
                titleColor = MaterialTheme.colorScheme.error,
                onClick = {viewModel.clearAllMockNotifications()}
            )
        }
    }
}