package com.silence.wanandroid.main.common

import androidx.annotation.ColorInt
import androidx.compose.foundation.layout.*
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes

class CommonComponent {
}


fun <T> T.asState(): MutableState<T> {
    return mutableStateOf(this)
}

fun <T> T.asLiveData(): MutableLiveData<T> {
    return MutableLiveData(this)
}

fun Boolean.selectColor(selectColor: Color, unSelectColor: Color): Color {
    return if (this) selectColor else unSelectColor
}

@Composable
fun TopAppBar(
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {}
) {

    Surface(
        color = SilenceColors.colorMain,
        contentColor = SilenceColors.colorMain,
        elevation = AppBarDefaults.TopAppBarElevation,
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