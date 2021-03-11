package com.silence.wanandroid.net.model

import com.silence.wanandroid.net.Empty

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Empty
data class BaseBean<T>(
    var errorCode: Int,
    var errorMsg: String,
    var data: T
)