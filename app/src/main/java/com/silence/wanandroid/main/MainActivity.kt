package com.silence.wanandroid.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.silence.wanandroid.base.BaseActivity
import com.silence.wanandroid.main.ui.MainPage

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainPage()
        }
    }
}