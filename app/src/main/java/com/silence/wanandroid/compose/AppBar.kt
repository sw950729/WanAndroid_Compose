package com.silence.wanandroid.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.AppBarDefaults.TopAppBarElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = TopAppBarElevation
) {
    TopAppBar(
        title, modifier, navigationIcon, actions, backgroundColor, contentColor, elevation,
    )
}
