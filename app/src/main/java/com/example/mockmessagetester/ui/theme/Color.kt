//负责定义低饱和度的莫兰迪色卡
package com.example.mockmessagetester.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * InboxZero 极简防焦虑色板 (Morandi Palette)
 * 核心设计理念：低饱和度、低视觉刺激、降低认知负荷
 */

// ==========================================
// 1. 主色调 (Primary) - 柔和莫兰迪色系
// ==========================================
// 莫兰迪蓝：用于 PrimaryButton、核心确认操作。传递宁静、理智的暗示。
val MorandiBlue = Color(0xFF86A3B8)
val MorandiBlueDark = Color(0xFF6B8A9E)

// ==========================================
// 2. 状态色 (State / Secondary) - 避免高亮大红大绿
// ==========================================
// 莫兰迪红（灰粉色）：用于“停止”状态或警示。温和的制止感，不引发心跳加速。
val MorandiRed = Color(0xFFD4A3A3)
// 莫兰迪绿（鼠尾草绿）：用于“运行中”或成功状态。
val MorandiGreen = Color(0xFFA8C2A7)

// ==========================================
// 3. 中性背景色 (Background & Surface) - 杜绝纯黑纯白的高对比眩光
// ==========================================
// 浅色模式 (Light Theme)
val BackgroundLight = Color(0xFFF7F8FA) // 极浅灰白，给界面提供呼吸感
val SurfaceLight = Color(0xFFFFFFFF)    // 纯白，用于 PrimaryContainer 的底色，与背景产生极弱层级

// 深色模式 (Dark Theme)
val BackgroundDark = Color(0xFF1E1E20)  // 炭灰，绝不使用纯黑 (#000000)
val SurfaceDark = Color(0xFF28282B)     // 略亮的深灰，用于深色模式下的卡片底色

// ==========================================
// 4. 文字排版色 (Typography) - 控制对比度
// ==========================================
// 浅色模式下的文字
val TextPrimaryLight = Color(0xFF2C3E50)   // 深灰蓝，代替纯黑（减轻视觉疲劳）
val TextSecondaryLight = Color(0xFF8898AA) // 中灰，用于次要提示信息

// 深色模式下的文字
val TextPrimaryDark = Color(0xFFE2E8F0)    // 柔和灰白，代替纯白（防止黑暗环境下的散光光晕）
val TextSecondaryDark = Color(0xFFA0AEC0)  // 暗灰，深色模式次要文字