package com.silence.wanandroid.main.mine

import com.silence.wanandroid.R

class FunctionItem(
    var iconSource: Int = R.mipmap.silence_mine_icon,
    var title: String = "",
    var subTitle: String = "",
    var arrowText: String = "",
    var onClick: () -> Unit
)
