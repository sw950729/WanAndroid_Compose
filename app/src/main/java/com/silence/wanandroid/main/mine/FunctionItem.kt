package com.silence.wanandroid.main.mine

import com.silence.wanandroid.R

data class FunctionItem(
    var iconSource: Int = R.mipmap.silence_mine_icon,
    var title: String = "测试内容",
    var subTitle: String = "",
    var arrowSource: Int = R.mipmap.arror_right_black,
    var arrowText: String = "",
    var onClick: () -> Unit
)
