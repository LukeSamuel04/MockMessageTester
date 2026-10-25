package com.example.mockmessagetester.ui.pages.testbenchpages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// 注意导入你刚刚写的表盘组件（如果 Android Studio 报错，请按 Alt+Enter 自动导入正确的包名）
import com.example.mockmessagetester.ui.components.dials.BpmConfigDial

@Composable
fun BpmConfigDialTestBenchPage() {
    // ==========================================
    // 1. 模拟外部大总管 (MockSenderManager) 的状态
    // ==========================================
    var bpm by remember { mutableIntStateOf(1) }
    var isRunning by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "节拍器旋钮测试台",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // ==========================================
            // 2. 挂载我们纯粹的无状态组件
            // ==========================================
            BpmConfigDial(
                bpm = bpm,
                onBpmChange = { newBpm ->
                    // 接收到滑动信号，更新外部状态
                    bpm = newBpm
                },
                isRunning = isRunning,
                onCenterClick = {
                    // 点击中心，切换运行状态
                    isRunning = !isRunning
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f) // 让表盘占据屏幕 85% 的宽度
            )

            Spacer(modifier = Modifier.height(56.dp))

            // ==========================================
            // 3. 外部状态监视器与控制区
            // ==========================================
            Text(
                text = "当前拦截到的输出信号: $bpm BPM",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "引擎状态: ${if (isRunning) "🔴 发送轰炸中..." else "⚪ 已停止"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 测试“一键重置”功能，验证表盘能否被外部力量强行扳回原位
            Button(
                onClick = {
                    bpm = 1
                    isRunning = false
                }
            ) {
                Text("一键恢复默认 (1 BPM)")
            }
        }
    }
}