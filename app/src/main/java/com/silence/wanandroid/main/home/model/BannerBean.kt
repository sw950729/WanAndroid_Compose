package com.silence.wanandroid.main.home.model

import com.silence.wanandroid.net.Empty

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Empty
data class BannerBean(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)