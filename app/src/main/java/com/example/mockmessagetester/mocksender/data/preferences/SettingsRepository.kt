package com.example.mockmessagetester.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// 1. 顶层声明 DataStore 扩展属性，保证在整个 App 运行期间是单例的
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

// 2. 定义数据状态类，把零散的布尔值打包成一个干净的业务对象
data class UserSettings(
    val isDarkMode: Boolean = false,
    val isHapticEnabled: Boolean = true,
    val appendTimestamp: Boolean = true,
    val autoGroup: Boolean = false
)

// 3. 仓库层具体实现
class SettingsRepository(private val context: Context) {

    // 定义所有的 Key
    private object Keys {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val IS_HAPTIC_ENABLED = booleanPreferencesKey("is_haptic_enabled")
        val APPEND_TIMESTAMP = booleanPreferencesKey("append_timestamp")
        val AUTO_GROUP = booleanPreferencesKey("auto_group")
    }

    // 暴露核心的数据流。UI 层只要监听这个 Flow，这里面的值一旦改变，UI 就会瞬间自动刷新。
    val userSettingsFlow: Flow<UserSettings> = context.dataStore.data
        .catch { exception ->
            // 工业级防呆处理：如果读取本地文件时磁盘抛异常，不要让 App 崩溃，而是发射一个空的偏好设置
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserSettings(
                // 如果是用户第一次安装打开，找不到对应的值，就使用 ?: 后面的默认值
                isDarkMode = preferences[Keys.IS_DARK_MODE] ?: false,
                isHapticEnabled = preferences[Keys.IS_HAPTIC_ENABLED] ?: true,
                appendTimestamp = preferences[Keys.APPEND_TIMESTAMP] ?: true,
                autoGroup = preferences[Keys.AUTO_GROUP] ?: false
            )
        }

    // ==========================================
    // 写入操作 (挂起函数，由 ViewModel 的协程调用，绝不卡顿主线程)
    // ==========================================

    suspend fun updateDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences -> preferences[Keys.IS_DARK_MODE] = isDark }
    }

    suspend fun updateHaptic(enabled: Boolean) {
        context.dataStore.edit { preferences -> preferences[Keys.IS_HAPTIC_ENABLED] = enabled }
    }

    suspend fun updateTimestamp(append: Boolean) {
        context.dataStore.edit { preferences -> preferences[Keys.APPEND_TIMESTAMP] = append }
    }

    suspend fun updateAutoGroup(auto: Boolean) {
        context.dataStore.edit { preferences -> preferences[Keys.AUTO_GROUP] = auto }
    }
}