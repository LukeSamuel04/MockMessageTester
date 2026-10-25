//负责定义防焦虑的全局字体大小与行高
package com.example.mockmessagetester.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * InboxZero 极简防焦虑排版库 (Typography)
 * 核心设计理念：
 * 1. 减少字号层级，避免视觉主次混乱。
 * 2. 适当增加行高，提供充足的“呼吸感”。
 * 3. 避免过粗的字重（FontWeight），降低视觉压迫感。
 */
val Typography = Typography(
    // 页面主标题 / 核心数据展示 (例如：“已屏蔽 12 条通知”)
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),

    // 正文内容 / 日志输出 / 描述性文本
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal, // 使用标准字重，保持克制
        fontSize = 16.sp,
        lineHeight = 26.sp, // 偏大的行高，提升易读性，降低密集恐惧
        letterSpacing = 0.1.sp
    ),

    // 按钮文字 / 标签 / 状态提示小字
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)