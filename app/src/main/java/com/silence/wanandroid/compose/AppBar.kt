package com.silence.wanandroid.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.AppBarDefaults.TopAppBarElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/

@Composable
fun TopAppBar(
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {}
) {

    Surface(
        color = SilenceColors.colorMain,
        contentColor = SilenceColors.colorMain,
        elevation = TopAppBarElevation,
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(SilenceSizes.appBarHeight)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            navigationIcon()
            Spacer(modifier = Modifier.weight(1f))
            action()
        }
    }
}
