package com.example.mockmessagetester.mocksender.data.mockmessages.corpus

import com.example.mockmessagetester.mocksender.data.mockmessages.MockMessage
import com.example.mockmessagetester.mocksender.data.mockmessages.MockScenario

/**
 * 测试剧本 01：论文死线前的兵荒马乱
 * 测试目的：验证系统在面对【高优单聊】+【高频群聊】+【系统级噪音】交叉轰炸时的处理与展示逻辑。
 */
val thesisCrisisScenario = MockScenario(
    scenarioId = "scenario_thesis_crisis_01",
    scenarioName = "论文死线：导师与外卖的交响曲",
    description = "模拟正在被导师追问论文进度时，被沙雕室友群聊和外卖短信疯狂打断的真实场景。",
    messages = listOf(
        // 1. 故事开端：导师突然发难
        MockMessage(
            appName = "微信",
            sender = "王导 (毕业论文)",
            content = "在吗？你上周交过来的第三章初稿我看了，数据部分好像有点问题。",
            threadId = "thesis_review",
            step = 1,
            // 导师发完这句，通常会等学生回复。这里模拟等待 6 秒钟
            delayAfterMs = 6000L
        ),

        // 2. 尴尬的等待期：突然插入无关的验证码短信
        MockMessage(
            appName = "短信",
            sender = "106980001234",
            content = "【阿里云】您的登录验证码是 849201，5分钟内有效。打死也不能告诉别人哦！",
            threadId = "noise_sms",
            step = 1,
            // 短信是系统并发的，发完后紧接着 1.5 秒导师又说话了
            delayAfterMs = 1500L
        ),

        // 3. 导师继续施压
        MockMessage(
            appName = "微信",
            sender = "王导 (毕业论文)",
            content = "图表 3-2 的纵坐标单位是不是标错了？你再核对一下原始数据。",
            threadId = "thesis_review",
            step = 2,
            // 导师打完字，正在思考下一句，停顿 3 秒
            delayAfterMs = 3000L
        ),

        // 4. 导师补充要求
        MockMessage(
            appName = "微信",
            sender = "王导 (毕业论文)",
            content = "另外，这周末我们需要开个线上会，把整体逻辑再理一遍。你看到消息回复我一下。",
            threadId = "thesis_review",
            step = 3,
            // 抛出终极问题，等待 4 秒
            delayAfterMs = 4000L
        ),

        // 5. 灾难降临：舍友群聊突然开始高频灌水 (连续 3 条短停顿消息)
        MockMessage(
            appName = "微信",
            sender = "张三",
            groupName = "404 摸鱼小分队",
            content = "兄弟们！黑神话悟空我下好了！",
            threadId = "roommate_gaming",
            step = 1,
            // 激动打字，极短停顿
            delayAfterMs = 800L
        ),
        MockMessage(
            appName = "微信",
            sender = "李四",
            groupName = "404 摸鱼小分队",
            content = "卧槽牛逼，赶紧上号",
            threadId = "roommate_gaming",
            step = 2,
            // 回复极快
            delayAfterMs = 1200L
        ),
        MockMessage(
            appName = "微信",
            sender = "张三",
            groupName = "404 摸鱼小分队",
            content = "今晚谁也别想睡，直接通宵走起！！！",
            threadId = "roommate_gaming",
            step = 3,
            // 群聊暂时告一段落，停顿 5 秒
            delayAfterMs = 5000L
        ),

        // 6. 现实的呼唤：外卖到了
        MockMessage(
            appName = "短信",
            sender = "美团外卖",
            content = "【美团】您的外卖已放置在南门外卖柜 03 格，请凭取件码 5581 及时取餐，祝您用餐愉快！",
            threadId = "noise_delivery",
            step = 1,
            // 最后一条消息，剧本结束，给个 0 延迟即可
            delayAfterMs = 0L
        )
    )
)