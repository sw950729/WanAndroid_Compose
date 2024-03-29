package com.silence.wanandroid.login.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.silence.wanandroid.MyApplication
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.compose.*
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.login.RegisterActivity
import com.silence.wanandroid.main.common.asState
import com.silence.wanandroid.main.common.toastOnUI
import com.silence.wanandroid.main.mine.model.UserInfo
import com.silence.wanandroid.net.RxNetWork
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun LoginPage() {
    val userInfo = MyApplication.getApp().userState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SilenceTopAppBar(title = "登录")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
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
                userInfo,
                false,
                submitterComposable = { enable, loginName, password, _ ->
                    LoginSubmitter(
                        enable,
                        loginName,
                        password
                    )
                })
            LoginToRegister()
        }
    }
}

@Composable
fun LoginToRegister() {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Text(
            text = "去注册",
            style = TextStyle(color = SilenceColors.colorMain, fontSize = SilenceSizes.textSize14),
            modifier = Modifier.clickable {
                Router.rout(RegisterActivity::class.java)
            }
        )
    }
}

@Composable
fun AccountPasswordFormArea(
    userInfo: MutableState<UserInfo>?,
    showRepeatPassword: Boolean = false,
    submitterComposable: @Composable (
        enable: Boolean, loginName: String, password: String,
        repeatPassword: String
    ) -> Unit
) {
    val userName = userInfo?.value?.publicName ?: ""
    var account by remember { userName.asState() }
    var password by remember { "".asState() }
    var repeatPassword by remember { "".asState() }

    SilenceNormalInput(
        labelText = "用户名",
        placeholderText = "请输入用户名",
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        account = it
    }
    SilencePasswordInput(
        labelText = "密码",
        placeholderText = "请输入密码",
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        password = it
    }

    if (showRepeatPassword) {
        SilencePasswordInput(
            labelText = "确认密码",
            placeholderText = "请输入密码",
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            repeatPassword = it
        }
    }

    submitterComposable(
        enable = account.isNotEmpty() && password.isNotEmpty()
                && (!showRepeatPassword || (showRepeatPassword && repeatPassword.isNotEmpty())),
        loginName = account,
        password = password,
        repeatPassword = repeatPassword
    )
}

@Composable
fun LoginSubmitter(enable: Boolean = false, loginName: String, password: String) {
    OnceClickButton(
        enable = enable,
        content = {
            Text(
                text = "登录",
                style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize16)
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) { enableButton ->
        GlobalScope.launch {
            with(RxNetWork.getObserverHttps().loginByPassword(loginName, password)) {
                Log.i("LoginPage", this.toString())
                if (0 == this.errorCode) {
                    this.data?.let {
                        MyApplication.getApp().updateUser(it)
                        toastOnUI("登录成功")
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
