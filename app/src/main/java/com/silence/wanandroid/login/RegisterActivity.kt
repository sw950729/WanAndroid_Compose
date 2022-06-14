package com.silence.wanandroid.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.silence.wanandroid.base.BaseActivity
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.login.ui.RegisterPage

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = SilenceColors.themeColors) {
                RegisterPage()
            }
        }
    }
}