package com.silence.wanandroid.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.login.RegisterPage

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = SilenceColors.themeColors) {
                RegisterPage()
            }
        }
    }
}