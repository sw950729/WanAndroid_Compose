package com.silence.wanandroid.login

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silence.wanandroid.R
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.main.common.TopAppBar
import com.silence.wanandroid.main.common.asState
import com.silence.wanandroid.main.mine.UserInfo
import com.silence.wanandroid.main.mine.logout
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun LoginPage() {
    // mock数据，此页面若用户为首次登录时
    val userInfo = UserInfo().logout()
    // mock数据，若用户历史有登录过
    // val userInfo = UserInfo().last()

    TopAppBar(title = "登录", navigationIcon = {
        SilenceIcon(
            painterResource(id = R.mipmap.logout),
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SilenceSizes.normalContentStartPadding,
                end = SilenceSizes.normalContentEndPadding
            )
    ) {
        LoginHeaderArea(userInfo)
        var accountInputState by remember { TextFieldValue(userInfo.loginName).asState() }
        var passwordInputState by remember { TextFieldValue("").asState() }
        var keyboardOptions by remember { KeyboardOptions(keyboardType = KeyboardType.Password).asState() }

        TextField(
            value = accountInputState,
            textStyle = TextStyle(fontSize = 16.sp),
            onValueChange = {
                accountInputState = it
            },
            label = { Text(text = "登录名") },
            placeholder = { Text(text = "请输入登录名") },
            leadingIcon = { LeadingIcon(id = R.mipmap.coin_count) },
            trailingIcon = {
                if (accountInputState.text.isNotEmpty()) {
                    LeadingIcon(id = R.mipmap.coin_count, modifier = Modifier.clickable {
                        accountInputState = TextFieldValue("")
                    })
                }
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            colors = textFieldColors(backgroundColor = Color.White)
        )

        TextField(
            value = passwordInputState,
            keyboardOptions = keyboardOptions,
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 16.sp),
            onValueChange = {
                passwordInputState = it
            },
            label = { Text(text = "密码") },
            placeholder = { Text(text = "请输入密码") },
            leadingIcon = { LeadingIcon(id = R.mipmap.coin_count) },
            trailingIcon = {
                if (passwordInputState.text.isNotEmpty()) {
                    LeadingIcon(id = R.mipmap.coin_count, modifier = Modifier.clickable {
                        keyboardOptions =
                            if (keyboardOptions.keyboardType == KeyboardType.Password) {
                                KeyboardOptions(keyboardType = KeyboardType.Text)
                            } else {
                                KeyboardOptions(keyboardType = KeyboardType.Password)
                            }
                    })
                }
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            colors = textFieldColors(backgroundColor = Color.White)
        )

        LoginSubmitter(
            enable = accountInputState.text.isNotEmpty() && passwordInputState.text.isNotEmpty(),
            loginName = accountInputState.text,
            password = passwordInputState.text
        )
    }
}

@Composable
fun LoginHeaderArea(userInfo: UserInfo) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(SilenceSizes.Login.loginHeaderPadHeight)
    ) {
        val imageData: Any =
            if (userInfo.avatarUrl == "") R.mipmap.ic_launcher else userInfo.avatarUrl
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
fun LeadingIcon(@DrawableRes id: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "",
        modifier = modifier.size(42.dp)
    )
}

@Composable
fun LoginSubmitter(enable: Boolean = false, loginName: String, password: String) {
    Log.i("LoginPage", "enable = $enable，登录名：$loginName , 密码：$password")
    Button(enabled = enable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        colors = buttonColors(
            backgroundColor = SilenceColors.colorMain,
            disabledBackgroundColor = Color.Gray
        ), onClick = {
            Log.i("Loginnnnnn", "点击了登录，登录名：$loginName , 密码：$password")
        }) {
        Text(
            text = "登录",
            style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize16)
        )
    }

}


