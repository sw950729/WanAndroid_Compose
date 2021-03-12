package com.silence.wanandroid.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.silence.wanandroid.config.SilenceColors

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe: 目前只支持text，后续其他场景如果非text，在考虑定制化
 **/

@Composable
fun TextTabLayoutComponent(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedIndex: Int,
    selectedColor: Color = SilenceColors.colorMain,
    unselectedColor: Color = Color.White,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    },
    onItemSelected: (Int) -> Unit,
) {
    TabLayoutComponent(
        modifier = modifier,
        count = items.count(),
        selectedIndex = selectedIndex,
        selectedColor,
        unselectedColor,
        indicator,
        onItemSelected = onItemSelected
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = items[it])
        }
    }
}

@Composable
fun TabLayoutComponent(
    modifier: Modifier = Modifier,
    count: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unselectedColor: Color,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    },
    divider: @Composable () -> Unit = @Composable {
        TabRowDefaults.Divider()
    },
    onItemSelected: (Int) -> Unit,
    tabContent: @Composable (Int) -> Unit,
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedIndex,
        backgroundColor = SilenceColors.colorMain,
        indicator = indicator,
        divider = divider
    ) {
        for (i in 0 until count) {
            Tab(
                selected = selectedIndex == i,
                onClick = {
                    onItemSelected(i)
                },
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor,
            ) {
                tabContent.invoke(i)
            }
        }
    }
}