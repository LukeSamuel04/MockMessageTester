package com.example.mockmessagetester.ui.screens.mocklab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mockmessagetester.ui.components.panels.mocksenderpanel.MockSenderControlPanel

@Composable
fun MockLabScreen(
    modifier: Modifier = Modifier,
    viewModel: MockLabViewModel = viewModel()
) {
    // 1. 获取全局状态快照
    val uiState by viewModel.uiState.collectAsState()

    // 2. 准备 UI 衍生数据
    val scenarioNames = viewModel.availableScenarios.map { it.scenarioName }
    val selectedScenarioName = viewModel.availableScenarios
        .find { it.scenarioId == uiState.selectedScenarioId }?.scenarioName ?: ""

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 3. 将包含 isHapticEnabled 的 uiState 向下传给控制面板
        MockSenderControlPanel(
            state = uiState,
            availableScenarioNames = scenarioNames,
            selectedScenarioName = selectedScenarioName,
            onModeChange = { viewModel.changeMode(it) },
            onManualTextChanged = { viewModel.updateManualText(it) },
            onSendManualClick = { viewModel.sendManualMessage() },
            onBpmChanged = { viewModel.updateBpm(it) },
            onScenarioSelected = { viewModel.updateSelectedScenario(it) },
            onToggleBurstClick = { viewModel.toggleBurst() },
            onResetBurstClick = { viewModel.resetBurst() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}