package com.example.mockmessagetester.mocksender.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * 通知发射核心 (Core)：负责持有系统 Context 通行证，向 Android 状态栏投递通知
 */
class NotificationDispatcher(private val context: Context) {

    // 1. 内部持有的状态：系统通知大管家与递增的通知 ID
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var currentNotificationId = 1000

    // 【新增管线接收器】：折叠状态开关，交由 ViewModel 实时同步
    var isAutoGroupEnabled: Boolean = false

    companion object {
        private const val CHANNEL_ID = "mock_wechat_channel"
        private const val CHANNEL_NAME = "模拟微信消息通道"
        private const val GROUP_KEY_WECHAT = "com.example.inboxzero.WECHAT_GROUP"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "用于 HCI 实验原型测试的假消息发射通道"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendSingle(sender: String, content: String) {
        currentNotificationId++

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle(sender)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // 【核心修改】：根据开关动态决定是否注入 Group 标识
        if (isAutoGroupEnabled) {
            builder.setGroup(GROUP_KEY_WECHAT)
        }

        notificationManager.notify(currentNotificationId, builder.build())
    }

    fun sendFoldedSummary(groupName: String, messages: List<String>) {
        currentNotificationId++

        val inboxStyle = NotificationCompat.InboxStyle()
            .setBigContentTitle("$groupName (${messages.size}条新消息)")
            .setSummaryText("微信模拟折叠")

        for (msg in messages) {
            inboxStyle.addLine(msg)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle(groupName)
            .setContentText("[${messages.size}条] ${messages.lastOrNull() ?: ""}")
            .setStyle(inboxStyle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setGroupSummary(true)
            .setAutoCancel(true)

        if (isAutoGroupEnabled) {
            builder.setGroup(GROUP_KEY_WECHAT)
        }

        notificationManager.notify(currentNotificationId, builder.build())
    }

    fun clearAll() {
        notificationManager.cancelAll()
    }
}