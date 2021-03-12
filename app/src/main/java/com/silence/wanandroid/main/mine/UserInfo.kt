package com.silence.wanandroid.main.mine

import com.silence.wanandroid.net.Empty

@Empty
data class UserInfo(
    var id: Long = -1,
    var level: String = "-1",
    var nickName: String = "暂未登录",
    var rank: Long = -1,
    var integration: Long = -1,
    var avatarUrl: String = "https://cdn.jsdelivr.net/gh/sw950729/img_cdn/img/20210220154843.jpeg"
)
