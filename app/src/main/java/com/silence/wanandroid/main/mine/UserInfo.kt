package com.silence.wanandroid.main.mine

import com.silence.wanandroid.net.Empty

@Empty
data class UserInfo(
    var id: Long = -1,
    var token: String = "",
    var level: String = "-1",
    var nickName: String = "暂未登录",
    var publicName: String = "",
    var rank: Long = -1,
    var integration: Long = -1,
    var icon: String = "https://cdn.jsdelivr.net/gh/sw950729/img_cdn/img/20210220154843.jpeg",
    var userName: String = "",
    var admin: Boolean = false,
    var email: String = "",
    var type: Int = 0,
)

fun UserInfo.logout(): UserInfo = this.let { info ->
    info.id = -1
    info.level = "-1"
    info.nickName = "暂未登录"
    info.rank = -1
    info.integration = -1
    info.icon = ""
    info.userName = ""
    return info
}

fun UserInfo.last(): UserInfo = this.let { info ->
    info.id = 123
    info.level = "-1"
    info.nickName = "暂未登录"
    info.rank = -1
    info.integration = -1
    info.icon = ""
    info.userName = "zhangsan"
    return info
}
