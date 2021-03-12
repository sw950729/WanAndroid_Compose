package com.silence.wanandroid.compose

import androidx.compose.foundation.layout.ColumnScope.Companion.align
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.silence.wanandroid.config.SilenceColors

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun LoadingProgress() {
    CircularProgressIndicator(
        modifier = Modifier
            .defaultMinSize(
                minHeight = ButtonDefaults.MinHeight,
            )
            .padding(ButtonDefaults.ContentPadding)
            .align(Alignment.CenterHorizontally),
        color = SilenceColors.colorMain
    )
}
