package com.example.mockmessagetester

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mockmessagetester.data.preferences.SettingsRepository
import com.example.mockmessagetester.data.preferences.UserSettings
import com.example.mockmessagetester.ui.screens.navigation.MainPagerScreen
import com.example.mockmessagetester.ui.theme.InboxZeroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // 1. 在顶层获取 Repository，并将其转换为 Compose 可监听的 State
            val repository = remember { SettingsRepository(applicationContext) }
            val uiState by repository.userSettingsFlow.collectAsState(initial = UserSettings())

            // 2. 将数据流中的 isDarkMode 强制下发给全站主题引擎
            InboxZeroTheme(darkTheme = uiState.isDarkMode) {
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { /* 极简处理 */ }
                )

                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                // 全局唯一的 Scaffold，统管整个 App 的背景和系统边距
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainPagerScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}