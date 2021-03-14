package com.silence.wanandroid.login

import android.os.Bundle
import androidx.activity.compose.setContent
import com.silence.wanandroid.base.BaseActivity

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPage()
        }
    }
}