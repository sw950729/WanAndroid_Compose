package com.silence.wanandroid.compose

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/
@Composable
fun SilenceIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    Icon(
        imageVector,
        null,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun SilenceIcon(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    Icon(
        bitmap,
        null,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun SilenceIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {

    Icon(
        painter,
        null,
        modifier = modifier,
        tint = tint
    )
}