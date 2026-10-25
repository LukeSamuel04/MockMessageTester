package com.example.mockmessagetester.mocksender.worker

import com.example.mockmessagetester.mocksender.core.NotificationDispatcher
import com.example.mockmessagetester.mocksender.data.mockmessages.MockMessage
import com.example.mockmessagetester.mocksender.data.mockmessages.MockScenario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AutoBurstWorker(private val dispatcher: NotificationDispatcher) {

    private var burstJob: Job? = null
    private val workerScope = CoroutineScope(Dispatchers.Default)

    private var repeatCounter = 0
    private var burstStartTime = 0L // 相对时间戳锚点

    val isRunning: Boolean
        get() = burstJob?.isActive == true

    // [修复 Bug 3] 参数类型改为函数 () -> Boolean
    fun startRepeatMode(
        targetMessage: MockMessage,
        intervalMs: Long,
        isTimestampEnabled: () -> Boolean,
        onProgress: (MockMessage) -> Unit
    ) {
        if (isRunning) return
        burstStartTime = System.currentTimeMillis()

        burstJob = workerScope.launch {
            while (isActive) {
                repeatCounter++

                // [修复 Bug 1] 解绑逻辑：不管开没开时间戳，都必须先拼接上序号
                val baseContent = "${targetMessage.content} ($repeatCounter)"

                // 然后再去动态查询开关状态，决定是否追加时间戳
                val finalContent = if (isTimestampEnabled()) {
                    val relativeMs = System.currentTimeMillis() - burstStartTime
                    "$baseContent @${relativeMs}ms"
                } else {
                    baseContent
                }

                dispatcher.sendSingle(
                    sender = targetMessage.sender,
                    content = finalContent
                )

                val sentMsg = targetMessage.copy(content = finalContent)
                onProgress(sentMsg)

                delay(intervalMs)
            }
        }
    }

    // [修复 Bug 3] 参数类型改为函数 () -> Boolean
    fun startScriptMode(
        scenario: MockScenario,
        isTimestampEnabled: () -> Boolean,
        onProgress: (MockMessage) -> Unit,
        onFinish: () -> Unit
    ) {
        if (isRunning) return
        burstStartTime = System.currentTimeMillis()

        burstJob = workerScope.launch {
            for (msg in scenario.messages) {
                if (!isActive) break

                val displayTitle = msg.groupName ?: msg.sender
                val baseContent = if (msg.groupName != null) {
                    "${msg.sender}: ${msg.content}"
                } else {
                    msg.content
                }

                // 动态查询开关状态
                val finalContent = if (isTimestampEnabled()) {
                    val relativeMs = System.currentTimeMillis() - burstStartTime
                    "$baseContent @${relativeMs}ms"
                } else {
                    baseContent
                }

                dispatcher.sendSingle(sender = displayTitle, content = finalContent)

                val sentMsg = msg.copy(content = finalContent)
                onProgress(sentMsg)

                delay(msg.delayAfterMs)
            }

            if (isActive) {
                launch(Dispatchers.Main) { onFinish() }
                stopBurst()
            }
        }
    }

    fun stopBurst() {
        burstJob?.cancel()
        burstJob = null
    }

    fun resetCounters() {
        repeatCounter = 0
        burstStartTime = 0L
    }
}