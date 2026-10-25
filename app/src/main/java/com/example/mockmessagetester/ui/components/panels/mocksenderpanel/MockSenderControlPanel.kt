package com.example.mockmessagetester.ui.components.panels.mocksenderpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.mockmessagetester.ui.components.buttons.PrimaryButton
import com.example.mockmessagetester.ui.components.buttons.SecondaryButton
import com.example.mockmessagetester.ui.components.buttons.TextButton
import com.example.mockmessagetester.ui.components.containers.PrimaryContainer
import com.example.mockmessagetester.ui.components.dropdowns.PrimaryDropdown
import com.example.mockmessagetester.ui.components.textfields.PrimaryTextField
import com.example.mockmessagetester.ui.components.dials.BpmConfigDial
import com.example.mockmessagetester.ui.screens.mocklab.MockLabUiState

// 纯视觉契约，留在这里
enum class MockMode(val label: String) {
    MANUAL("手动单发 (Manual)"),
    REPEAT("无脑轰炸模式 (Repeat)"),
    SCRIPT("剧本情景模式 (Script)")
}

@Composable
fun MockSenderControlPanel(
    state: MockLabUiState,
    availableScenarioNames: List<String>,
    selectedScenarioName: String,
    onModeChange: (MockMode) -> Unit,
    onManualTextChanged: (String) -> Unit,
    onSendManualClick: () -> Unit,
    onBpmChanged: (Int) -> Unit,
    onScenarioSelected: (String) -> Unit,
    onToggleBurstClick: () -> Unit,
    onResetBurstClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryContainer(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Mock 消息发送控制台",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            val modeLabels = MockMode.entries.map { it.label }
            PrimaryDropdown(
                options = modeLabels,
                selectedOption = state.currentMode.label,
                onOptionSelected = { label ->
                    val newMode = MockMode.entries.first { it.label == label }
                    onModeChange(newMode)
                },
                label = "当前测试模式"
            )

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))

            when (state.currentMode) {
                MockMode.MANUAL -> {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        PrimaryTextField(
                            value = state.manualText,
                            onValueChange = onManualTextChanged,
                            placeholderText = "输入要测试发送的内容..."
                        )
                        PrimaryButton(
                            text = "发送单条消息",
                            onClick = onSendManualClick,
                            enabled = state.manualText.isNotBlank(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                MockMode.REPEAT -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PrimaryTextField(
                            value = state.manualText,
                            onValueChange = onManualTextChanged,
                            placeholderText = "输入用作高频轰炸的靶机内容..."
                        )
                        Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                            BpmConfigDial(
                                bpm = state.currentBpm,
                                onBpmChange = onBpmChanged,
                                isRunning = state.isRunning,
                                isHapticEnabled = state.isHapticEnabled,
                                onCenterClick = onToggleBurstClick,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                MockMode.SCRIPT -> {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        PrimaryDropdown(
                            options = availableScenarioNames,
                            selectedOption = selectedScenarioName,
                            onOptionSelected = onScenarioSelected,
                            label = "选择测试剧本"
                        )
                    }
                }
            }

            if (state.currentMode != MockMode.MANUAL) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "最新发射状态",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = state.lastSentMessagePreview,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.currentMode == MockMode.SCRIPT) {
                            if (!state.isRunning) {
                                PrimaryButton(text = "开始自动发射", onClick = onToggleBurstClick, modifier = Modifier.weight(1f))
                            } else {
                                SecondaryButton(text = "暂停发射", onClick = onToggleBurstClick, modifier = Modifier.weight(1f))
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        if (!state.isRunning) {
                            TextButton(text = "重置洗牌", onClick = onResetBurstClick)
                        }
                    }
                }
            }
        }
    }
}