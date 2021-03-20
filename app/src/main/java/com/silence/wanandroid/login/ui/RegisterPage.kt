package com.silence.wanandroid.login.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.compose.OnceClickButton
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.compose.SilenceTopAppBar
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.main.common.toastOnUI
import com.silence.wanandroid.net.RxNetWork
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @date:2021/3/20
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun RegisterPage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        SilenceTopAppBar(title = "注册")
        Column(
            modifier = Modifier.padding(
                start = SilenceSizes.normalContentStartPadding,
                end = SilenceSizes.normalContentEndPadding
            )
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            AccountPasswordFormArea(
                userInfo = null,
                showRepeatPassword = true,
                submitterComposable = { enable, loginName, password, repeatPassword ->
                    RegisterSubmitter(
                        enable,
                        loginName,
                        password,
                        repeatPassword
                    )
                })
        }
    }
}

@Composable
fun RegisterSubmitter(
    enable: Boolean = false,
    loginName: String,
    password: String,
    repeatPassword: String
) {
    OnceClickButton(
        enable = enable,
        content = {
            Text(
                text = "注册",
                style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize16)
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) { enableButton ->
        GlobalScope.launch {
            with(RxNetWork.getObserverHttps().register(loginName, password, repeatPassword)) {
                Log.i("RegisterPage", this.toString())
                if (0 == this.errorCode) {
                    this.data?.let {
                        toastOnUI("注册成功")
                        Router.back()
                    }
                } else {
                    toastOnUI(this.errorMsg)
                    enableButton()
                }
            }
        }
    }
}