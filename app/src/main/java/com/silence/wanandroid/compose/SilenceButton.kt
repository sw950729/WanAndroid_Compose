package com.silence.wanandroid.compose

import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.main.common.asState


/**
 * 仅可点击一次，需手动恢复可点状态
 */
@Composable
fun OnceClickButton(
    enable: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = SilenceColors.colorMain,
        disabledBackgroundColor = Color.Gray
    ),
    content: @Composable () -> Unit,
    modifier: Modifier,
    onClick: (enableButton: () -> Unit) -> Unit = {}
) {
    var buttonEnable by remember { true.asState() }
    Button(enabled = buttonEnable && enable,
        modifier = modifier,
        colors = colors,
        onClick = {
            buttonEnable = false
            onClick { buttonEnable = true }
        }) {
        content()
    }
}