package com.silence.wanandroid.main.mine

import android.util.Log
import com.silence.wanandroid.R

object MineFunctionList {

    val functions: List<FunctionItem> = listOf<FunctionItem>(
        FunctionItem(iconSource = R.mipmap.coin_count, title = "积分记录") {
            Log.i(
                "MineFunctionList",
                "积分记录"
            )
        },
        FunctionItem(iconSource = R.mipmap.collect, title = "我的收藏") {
            Log.i(
                "MineFunctionList",
                "我的收藏"
            )
        },
        FunctionItem(iconSource = R.mipmap.mine_share, title = "我的分享") {
            Log.i(
                "MineFunctionList",
                "我的分享"
            )
        },
        FunctionItem(iconSource = R.mipmap.web, title = "开源网站") {
            Log.i(
                "MineFunctionList",
                "开源网站"
            )
        },
        FunctionItem(iconSource = R.mipmap.about_mine, title = "关于我") {
            Log.i(
                "MineFunctionList",
                "关于我"
            )
        },
        FunctionItem(iconSource = R.mipmap.setting, title = "系统设置") {
            Log.i(
                "MineFunctionList",
                "系统设置"
            )
        },
        FunctionItem(iconSource = R.mipmap.logout, title = "退出登录") {
            Log.i(
                "MineFunctionList",
                "退出登录"
            )
        },
    )

}