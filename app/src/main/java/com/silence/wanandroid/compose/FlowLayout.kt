package com.silence.wanandroid.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> FlowLayout(
    datas: List<T>?,
    item: @Composable (T) -> Unit
) {
    Layout(content = {
        if (datas == null) {
            return
        }
        for (data in datas) {
            item(data)
        }
    }, measurePolicy = { measurables, constraints ->
        layout(constraints.maxWidth, constraints.minHeight) {
            var currentWidth = 0
            var currentHeight = 0
            measurables.map {
                it.measure(constraints) to it.parentData
            }.forEach { (placeable, _) ->
                // 预计算当前宽度 + 左右边距
                val preWidth =
                    currentWidth + placeable.width
                if (preWidth <= constraints.maxWidth) {
                    placeable.placeRelative(currentWidth, currentHeight)
                    currentWidth += placeable.width
                } else {
                    placeable.placeRelative(
                        0,
                        currentHeight + placeable.height
                    )
                    currentWidth = placeable.width
                    currentHeight += placeable.height
                }
            }
        }
    })
}


