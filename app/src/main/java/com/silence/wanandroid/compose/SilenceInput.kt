package com.silence.wanandroid.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.silence.wanandroid.R
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.login.ui.LoginSubmitter
import com.silence.wanandroid.main.common.asState
import com.silence.wanandroid.main.common.selectColor

@Composable
fun SilenceNormalInput(
    text: String = "",
    labelText: String = "",
    placeholderText: String = "",
    showTrailingIcon: Boolean = true,
    textStyle: TextStyle = TextStyle(fontSize = SilenceSizes.textSize16),
    focusChange: ((hasFocus: Boolean) -> Unit)? = null,
    modifier: Modifier,
    valueChange: ((value: String) -> Unit)? = null
) {
    var accountInputState by remember { TextFieldValue(text).asState() }

    var accountHasFocus by remember { false.asState() }

    TextField(
        value = accountInputState,
        textStyle = textStyle,
        onValueChange = {
            accountInputState = it
            valueChange?.let { it(accountInputState.text) }
        },
        label = {
            Text(
                text = labelText,
                style = TextStyle(
                    color = accountHasFocus.selectColor(
                        SilenceColors.colorMain,
                        Color.Gray
                    )
                )
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = TextStyle(color = Color.Gray)
            )
        },
        leadingIcon = {
            SilenceIcon(
                Icons.Filled.Person,
                modifier = Modifier
                    .size(SilenceSizes.padding25),
                tint = accountHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
            )
        },
        trailingIcon = {
            if (accountInputState.text.isNotEmpty() && showTrailingIcon) {
                SilenceIcon(
                    painter = painterResource(id = R.mipmap.clear),
                    modifier = Modifier
                        .size(SilenceSizes.padding25)
                        .clickable {
                            accountInputState = TextFieldValue("")
                            valueChange?.let { it("") }
                        },
                    tint = accountHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        ),
        modifier = modifier
            .onFocusChanged {
                accountHasFocus =
                    (it != FocusState.Disabled && it != FocusState.Inactive)
                Log.i("LoginPage", "passwordHasFocus =$accountHasFocus")
                focusChange?.let { it(accountHasFocus) }
            }
    )

}


@Composable
fun SilencePasswordInput(
    text: String = "",
    labelText: String = "",
    placeholderText: String = "",
    showTrailingIcon: Boolean = true,
    textStyle: TextStyle = TextStyle(fontSize = SilenceSizes.textSize16),
    focusChange: ((hasFocus: Boolean) -> Unit)? = null,
    modifier: Modifier,
    valueChange: ((value: String) -> Unit)? = null
) {
    var passwordInputState by remember { TextFieldValue(text).asState() }

    var passwordHasFocus by remember { false.asState() }

    var passwordShowType by remember { false.asState() }
    TextField(
        value = passwordInputState,
        keyboardOptions = if (passwordShowType) KeyboardOptions(keyboardType = KeyboardType.Text) else
            KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordShowType) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = textStyle,
        onValueChange = {
            passwordInputState = it
            valueChange?.let { it(passwordInputState.text) }
        },
        label = {
            Text(
                text = labelText,
                style = TextStyle(
                    color = passwordHasFocus.selectColor(
                        SilenceColors.colorMain,
                        Color.Gray
                    )
                )
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = TextStyle(color = Color.Gray)
            )
        },
        leadingIcon = {
            SilenceIcon(
                Icons.Filled.Lock,
                modifier = Modifier
                    .size(SilenceSizes.padding25),
                tint = passwordHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
            )
        },
        trailingIcon = {
            if (passwordInputState.text.isNotEmpty() && showTrailingIcon) {
                SilenceIcon(
                    painter = if (passwordShowType) painterResource(R.mipmap.eyes_close) else painterResource(
                        R.mipmap.eyes_open
                    ),
                    modifier = Modifier
                        .size(SilenceSizes.padding25)
                        .clickable {
                            passwordShowType = !passwordShowType
                        },
                    tint = passwordHasFocus.selectColor(SilenceColors.colorMain, Color.Gray)
                )
            }
        },
        modifier = modifier
            .onFocusChanged {
                passwordHasFocus =
                    (it != FocusState.Disabled && it != FocusState.Inactive)
                Log.i("LoginPage", "passwordHasFocus =$passwordHasFocus")
                focusChange?.let { it(passwordHasFocus) }
            },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
        )
    )

}

