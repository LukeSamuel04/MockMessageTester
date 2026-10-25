package com.example.mockmessagetester.ui.screens.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mockmessagetester.data.preferences.SettingsRepository
import com.example.mockmessagetester.ui.screens.mocklab.MockLabScreen
import com.example.mockmessagetester.ui.screens.settings.SettingsScreen
import com.example.mockmessagetester.ui.screens.settings.SettingsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPagerScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 2 })

// 获取当前上下文
    val context = LocalContext.current
    // 从上下文中提取出 Application 层级的 context
    val application = context.applicationContext as android.app.Application
    // 实例化底层的 Repository
    val settingsRepository = SettingsRepository(context)

    // 使用工厂模式创建 SettingsViewModel，传入刚刚准备好的两个参数
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.provideFactory(
            application = application,
            repository = settingsRepository
        )
    )
    Column(modifier = modifier.fillMaxSize()) {
        // 1. 滑动内容区
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> MockLabScreen()
                // 把刚建好的 viewModel 顺着管子塞进 SettingsScreen
                1 -> SettingsScreen(viewModel = settingsViewModel)
            }
        }

        // 2. 底部圆点指示器 (Pager Indicator)
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}