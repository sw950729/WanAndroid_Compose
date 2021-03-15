package com.silence.wanandroid.config

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/

object SilenceColors {


    val colorMain = Color(0xFF0084FF)

    val themeColors: Colors = Colors(
        primary = colorMain,
        primaryVariant = colorMain,
        secondary = colorMain,
        secondaryVariant = colorMain,
        background = Color.White,
        surface = colorMain,
        error = Color.Red,
        onPrimary = colorMain,
        onSecondary = colorMain,
        onBackground = Color.White,
        onSurface = colorMain,
        onError = Color.Red,
        isLight = true
    )

}