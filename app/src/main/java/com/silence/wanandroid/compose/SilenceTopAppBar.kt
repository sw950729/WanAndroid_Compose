package com.silence.wanandroid.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AppBarDefaults.TopAppBarElevation
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/

@Composable
@Preview
fun SilenceTopAppBar(
    title: String = "",
    showBack: Boolean = true,
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
                .padding(SilenceSizes.padding12)
        ) {
            if (showBack) {
                SilenceIcon(
                    Icons.Filled.ArrowBack,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = SilenceSizes.padding4)
                        .size(SilenceSizes.padding25)
                        .clickable {
                            Router.back()
                        }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = SilenceSizes.padding5)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize18),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            action()
        }
    }
}
