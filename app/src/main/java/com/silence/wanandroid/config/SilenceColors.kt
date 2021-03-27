package com.silence.wanandroid.config

import android.graphics.ColorSpace
import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/

object SilenceColors {

    val colorMain = Color(0xFF0084FF)

    val color_ef = Color(0xFFEFEFEF)

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

    fun random(): Color {
        // 数值越小，颜色越深
        val red: Float = (Random.Default.nextInt(60) + 20) / 100F
        val green: Float = (Random.Default.nextInt(70) + 10) / 100F
        val blue: Float = (Random.Default.nextInt(60) + 20) / 100F
        return Color(red, green, blue, alpha = 1f)
    }
}