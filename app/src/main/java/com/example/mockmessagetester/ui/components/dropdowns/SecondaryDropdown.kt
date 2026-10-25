package com.example.mockmessagetester.ui.components.dropdowns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * InboxZero 次级下拉框 (Secondary Dropdown)
 *
 * 核心设计规则 (HCI)：
 * 1. 极致隐身：放弃沉重的 TextField 结构，直接使用 Text + 极简箭头的形态，在未交互时伪装成普通文字。
 * 2. 无边框无底色：彻底避免视觉割裂，仅作为局部细微参数调整使用（如选择：500ms, 1s, 2s）。
 * 3. 空间收敛：不强占整行宽度，而是紧凑包裹内容，作为句子里的一部分存在。
 */
@Composable
fun SecondaryDropdown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // 不使用庞大的 ExposedDropdownMenuBox，而是手搓一个极其轻量且贴合文字的触发器
    Box(modifier = modifier) {
        // 触发器：一段文字 + 一个下拉箭头
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp)) // 给点击的水波纹一点轻微的圆角收束
                .clickable { expanded = true }
                .padding(horizontal = 8.dp, vertical = 4.dp) // 给一点点击热区，但不夸张
        ) {
            Text(
                text = selectedOption,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant // 使用偏灰的次要文字，进一步降维
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "展开选项",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // 悬浮菜单
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (option == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}