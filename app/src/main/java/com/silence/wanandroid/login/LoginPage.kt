package com.silence.wanandroid.login

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silence.wanandroid.MyApplication
import com.silence.wanandroid.R
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.main.common.TopAppBar
import com.silence.wanandroid.main.common.asState
import com.silence.wanandroid.main.common.selectColor
import com.silence.wanandroid.main.common.toastOnUI
import com.silence.wanandroid.main.mine.UserInfo
import com.silence.wanandroid.net.RxNetWork
import com.silence.wanandroid.register.RegisterActivity
import dev.chrisbanes.accompanist.coil.CoilImage
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
        TopAppBar(title = "登录", navigationIcon = {
            SilenceIcon(
                painterResource(id = R.mipmap.arrow_back),
                tint = Color.White,
                modifier = Modifier
                    .padding(end = SilenceSizes.padding4)
                    .size(24.dp)
                    .clickable {
                        Router.back()
                    }
            )
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = SilenceSizes.normalContentStartPadding,
                    end = SilenceSizes.normalContentEndPadding
                )
        ) {
            LoginHeaderArea(userInfo)
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
fun RegisterPage() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        TopAppBar(title = "注册", navigationIcon = {
            SilenceIcon(
                painterResource(id = R.mipmap.arrow_back),
                tint = Color.White,
                modifier = Modifier
                    .padding(end = SilenceSizes.padding4)
                    .size(24.dp)
                    .clickable {
                        Router.back()
                    }
            )
        })

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
    var accountInputState by remember { TextFieldValue(userName).asState() }
    var passwordInputState by remember { TextFieldValue("").asState() }

    var accountHasFocus by remember { false.asState() }
    var passwordHasFocus by remember { false.asState() }

    var passwordShowType by remember { false.asState() }

    TextField(
        value = accountInputState,
        textStyle = TextStyle(fontSize = 16.sp),
        onValueChange = {
            accountInputState = it
        },
        label = { Text(text = "登录名") },
        placeholder = { Text(text = "请输入登录名") },
        leadingIcon = {
            LeadingIcon(
                id = R.mipmap.login_account,
                modifier = Modifier
                    .size(32.dp),
                tint = accountHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
            )
        },
        trailingIcon = {
            if (accountInputState.text.isNotEmpty()) {
                LeadingIcon(
                    id = R.mipmap.clear,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            accountInputState = TextFieldValue("")
                        },
                    tint = accountHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
                )
            }
        },
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .onFocusChanged {
                accountHasFocus =
                    (it != FocusState.Disabled && it != FocusState.Inactive)
                Log.i("LoginPage", "passwordHasFocus =$accountHasFocus")
            },
        colors = textFieldColors(
            backgroundColor = Color.White
        )
    )

    TextField(
        value = passwordInputState,
        keyboardOptions = if (passwordShowType) KeyboardOptions(keyboardType = KeyboardType.Text) else
            KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordShowType) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = TextStyle(fontSize = 16.sp),
        onValueChange = {
            passwordInputState = it
        },
        label = { Text(text = "密码") },
        placeholder = { Text(text = "请输入密码") },
        leadingIcon = {
            LeadingIcon(
                id = R.mipmap.login_password,
                modifier = Modifier
                    .size(32.dp),
                tint = passwordHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
            )
        },
        trailingIcon = {
            if (passwordInputState.text.isNotEmpty()) {
                LeadingIcon(
                    id = if (passwordShowType) R.mipmap.eyes_close else R.mipmap.eyes_open,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            passwordShowType = !passwordShowType
                        },
                    tint = passwordHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
                )
            }
        },
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .onFocusChanged {
                passwordHasFocus =
                    (it != FocusState.Disabled && it != FocusState.Inactive)
                Log.i("LoginPage", "passwordHasFocus =$passwordHasFocus")
            },
        colors = textFieldColors(
            backgroundColor = Color.White,
        )
    )

    var repeatPasswordInputState by remember { TextFieldValue("").asState() }

    if (showRepeatPassword) {
        var repeatPasswordHasFocus by remember { false.asState() }

        var repeatPasswordShowType by remember { false.asState() }
        TextField(
            value = repeatPasswordInputState,
            keyboardOptions = if (repeatPasswordShowType) KeyboardOptions(keyboardType = KeyboardType.Text) else
                KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (repeatPasswordShowType) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 16.sp),
            onValueChange = {
                repeatPasswordInputState = it
            },
            label = { Text(text = "确认密码") },
            placeholder = { Text(text = "请输入密码") },
            leadingIcon = {
                LeadingIcon(
                    id = R.mipmap.login_password,
                    modifier = Modifier
                        .size(32.dp),
                    tint = repeatPasswordHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
                )
            },
            trailingIcon = {
                if (repeatPasswordInputState.text.isNotEmpty()) {
                    LeadingIcon(
                        id = if (repeatPasswordShowType) R.mipmap.eyes_close else R.mipmap.eyes_open,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                repeatPasswordShowType = !repeatPasswordShowType
                            },
                        tint = repeatPasswordHasFocus.selectColor(
                            SilenceColors.colorMain,
                            Color.Gray
                        )
                    )
                }
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    repeatPasswordHasFocus =
                        (it != FocusState.Disabled && it != FocusState.Inactive)
                    Log.i("LoginPage", "passwordHasFocus =$repeatPasswordHasFocus")
                },
            colors = textFieldColors(
                backgroundColor = Color.White,
            )
        )
    }

    submitterComposable(
        enable = accountInputState.text.isNotEmpty() && passwordInputState.text.isNotEmpty()
                && (!showRepeatPassword || (showRepeatPassword && repeatPasswordInputState.text.isNotEmpty())),
        loginName = accountInputState.text,
        password = passwordInputState.text,
        repeatPassword = repeatPasswordInputState.text
    )
}

@Composable
fun LoginHeaderArea(userInfo: MutableState<UserInfo>?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(SilenceSizes.Login.loginHeaderPadHeight)
    ) {
        val userIcon = userInfo?.value?.icon ?: ""
        val imageData: Any =
            if (userIcon == "") R.mipmap.ic_launcher else userIcon
        CoilImage(
            data = imageData,
            contentScale = ContentScale.FillBounds,
            contentDescription = "avatar",
            modifier = Modifier
                .width(SilenceSizes.mineUserAvatarHeight)
                .height(SilenceSizes.mineUserAvatarHeight)
                .clip(CircleShape),
            error = {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher),
                    contentDescription = "logo", modifier = Modifier
                        .width(SilenceSizes.mineUserAvatarHeight)
                        .height(SilenceSizes.mineUserAvatarHeight)
                        .clip(CircleShape)
                )
            }
        )
    }
}

@Composable
fun LeadingIcon(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
) {
    SilenceIcon(
        painter = painterResource(id = id),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun LoginSubmitter(enable: Boolean = false, loginName: String, password: String) {
    Log.i("LoginPage", "enable = $enable，用户名：$loginName , 密码：$password")
    Button(enabled = enable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        colors = buttonColors(
            backgroundColor = SilenceColors.colorMain,
            disabledBackgroundColor = Color.Gray
        ), onClick = {
            Log.i("LoginPage", "点击了登录，用户名：$loginName , 密码：$password")
            GlobalScope.launch {
                with(RxNetWork.getObserverHttps().loginByPassword(loginName, password)) {
                    Log.i("LoginPage", this.toString())
                    if (0 == this.errorCode) {
                        this.data?.let {
                            MyApplication.getApp().updateUser(it)
                            toastOnUI("登陆成功")
                            Router.back()
                        }
                    } else {
                        toastOnUI(this.errorMsg)
                    }
                }
            }
        }) {
        Text(
            text = "登录",
            style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize16)
        )
    }
}

@Composable
fun RegisterSubmitter(
    enable: Boolean = false,
    loginName: String,
    password: String,
    repeatPassword: String
) {
    Log.i("RegisterPage", "enable = $enable，用户名：$loginName , 密码：$password")
    Button(enabled = enable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        colors = buttonColors(
            backgroundColor = SilenceColors.colorMain,
            disabledBackgroundColor = Color.Gray
        ), onClick = {
            Log.i("RegisterPage", "点击了注册，用户名：$loginName , 密码：$password")
            GlobalScope.launch {
                with(RxNetWork.getObserverHttps().register(loginName, password, repeatPassword)) {
                    Log.i("RegisterPage", this.toString())
                    toastOnUI(this.errorMsg)
                    Router.back()
                }
            }
        }) {
        Text(
            text = "注册",
            style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize16)
        )
    }
}


