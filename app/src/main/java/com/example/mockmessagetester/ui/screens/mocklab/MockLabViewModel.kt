package com.example.mockmessagetester.ui.screens.mocklab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mockmessagetester.mocksender.core.NotificationDispatcher
import com.example.mockmessagetester.mocksender.data.mockmessages.MockMessage
import com.example.mockmessagetester.mocksender.data.mockmessages.corpus.thesisCrisisScenario
import com.example.mockmessagetester.mocksender.worker.AutoBurstWorker
import com.example.mockmessagetester.ui.components.panels.mocksenderpanel.MockMode
import com.example.mockmessagetester.data.preferences.SettingsRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. 页面级数据快照 (UiState)
data class MockLabUiState(
    val currentMode: MockMode = MockMode.MANUAL,
    val isRunning: Boolean = false,
    val manualText: String = "这是一条测试消息",
    val currentBpm: Int = 30,
    val selectedScenarioId: String? = null,
    val lastSentMessagePreview: String = "等待发射...",
    // 【新增管线】：触觉反馈状态
    val isHapticEnabled: Boolean = true
)

// 2. 页面级管理层 (ViewModel)
class MockLabViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MockLabUiState())
    val uiState: StateFlow<MockLabUiState> = _uiState.asStateFlow()

    val availableScenarios = listOf(thesisCrisisScenario)

    private val settingsRepository = SettingsRepository(application)
    private var isTimestampEnabled = false

    private val dispatcher = NotificationDispatcher(application)
    private val worker = AutoBurstWorker(dispatcher)

    init {
        val defaultScenario = availableScenarios.firstOrNull()
        if (defaultScenario != null) {
            _uiState.update { it.copy(selectedScenarioId = defaultScenario.scenarioId) }
        }

        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collect { settings ->
                // 更新内存中的时间戳开关
                isTimestampEnabled = settings.appendTimestamp

                // 将折叠开关实时同步给底层发信引擎
                dispatcher.isAutoGroupEnabled = settings.autoGroup

                // 【核心修改】：将设置里的震动开关实时同步给 UI 状态流
                _uiState.update { it.copy(isHapticEnabled = settings.isHapticEnabled) }
            }
        }
    }

    fun changeMode(newMode: MockMode) {
        if (newMode == _uiState.value.currentMode) return
        worker.stopBurst()
        worker.resetCounters()
        _uiState.update { it.copy(
            currentMode = newMode,
            isRunning = false,
            lastSentMessagePreview = "模式已切换，等待发射..."
        )}
    }

    fun updateManualText(newText: String) {
        _uiState.update { it.copy(manualText = newText) }
    }

    fun sendManualMessage() {
        val text = _uiState.value.manualText
        if (text.isNotBlank()) {
            dispatcher.sendSingle(sender = "手动单发测试", content = text)
            _uiState.update { it.copy(lastSentMessagePreview = "发送成功: $text") }
        }
    }

    fun updateBpm(bpm: Int) {
        _uiState.update { it.copy(currentBpm = bpm) }
    }

    fun updateSelectedScenario(scenarioName: String) {
        val matchedScenario = availableScenarios.find { it.scenarioName == scenarioName }
        if (matchedScenario != null) {
            _uiState.update { it.copy(selectedScenarioId = matchedScenario.scenarioId) }
        }
    }

    fun toggleBurst() {
        if (_uiState.value.isRunning) stopEngine() else startEngine()
    }

    fun resetBurst() {
        stopEngine()
        worker.resetCounters()
        _uiState.update { it.copy(lastSentMessagePreview = "弹药已重置洗牌，准备就绪") }
    }

    private fun startEngine() {
        val currentState = _uiState.value
        _uiState.update { it.copy(isRunning = true) }

        when (currentState.currentMode) {
            MockMode.REPEAT -> {
                val bpm = currentState.currentBpm.coerceAtLeast(1)
                val intervalMs = 60000L / bpm
                val targetMessage = MockMessage(
                    appName = "系统测试",
                    sender = "高频轰炸机",
                    content = currentState.manualText.ifBlank { "压力测试消息" }
                )
                worker.startRepeatMode(
                    targetMessage = targetMessage,
                    intervalMs = intervalMs,
                    isTimestampEnabled = { isTimestampEnabled },
                    onProgress = { sentMsg ->
                        _uiState.update { it.copy(lastSentMessagePreview = "${sentMsg.sender}: ${sentMsg.content}") }
                    }
                )
            }
            MockMode.SCRIPT -> {
                val scenario = availableScenarios.find { it.scenarioId == currentState.selectedScenarioId }
                if (scenario != null) {
                    worker.startScriptMode(
                        scenario = scenario,
                        isTimestampEnabled = { isTimestampEnabled },
                        onProgress = { sentMsg ->
                            val previewText = sentMsg.groupName?.let { "群聊[${it}] ${sentMsg.sender}: ${sentMsg.content}" }
                                ?: "${sentMsg.sender}: ${sentMsg.content}"
                            _uiState.update { it.copy(lastSentMessagePreview = previewText) }
                        },
                        onFinish = {
                            _uiState.update { it.copy(isRunning = false, lastSentMessagePreview = "✅ 剧本全部播送完毕，已自动杀青") }
                        }
                    )
                } else stopEngine()
            }
            MockMode.MANUAL -> _uiState.update { it.copy(isRunning = false) }
        }
    }

    private fun stopEngine() {
        worker.stopBurst()
        _uiState.update { it.copy(isRunning = false, lastSentMessagePreview = "已手动暂停") }
    }
}