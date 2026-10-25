package com.example.mockmessagetester.ui.screens.settings

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mockmessagetester.data.preferences.SettingsRepository
import com.example.mockmessagetester.data.preferences.UserSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val repository: SettingsRepository
) : AndroidViewModel(application) {

    // 1. 状态暴露：将底层的冷流转换为 UI 专用的热流 (StateFlow)
    val uiState: StateFlow<UserSettings> = repository.userSettingsFlow
        .stateIn(
            scope = viewModelScope, // 绑定 ViewModel 的生命周期
            // 工业级优化：当 UI 离开屏幕 5 秒后自动断开流监听，节约手机电量和内存
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings() // 等待磁盘读取时的默认初始状态
        )

    // ==========================================
    // 2. 意图接收器 (Events)：接收 UI 的点击事件
    // ==========================================

    fun updateDarkMode(isDark: Boolean) {
        // viewModelScope.launch 会自动在后台协程中安全执行，绝不阻塞主线程
        viewModelScope.launch {
            repository.updateDarkMode(isDark)
        }
    }

    fun updateHaptic(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateHaptic(enabled)
        }
    }

    fun updateTimestamp(append: Boolean) {
        viewModelScope.launch {
            repository.updateTimestamp(append)
        }
    }

    fun updateAutoGroup(auto: Boolean) {
        viewModelScope.launch {
            repository.updateAutoGroup(auto)
        }
    }

    // 新增：一键清空当前 App 发出的所有通知
    fun clearAllMockNotifications() {
        val notificationManager = getApplication<Application>()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.cancelAll()
    }

    // ==========================================
    // 3. ViewModel 工厂 (无依赖注入框架时的标准做法)
    // ==========================================
    companion object {
        fun provideFactory(
            application: Application,
            repository: SettingsRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                        return SettingsViewModel(application, repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}