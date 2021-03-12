package com.silence.wanandroid.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.silence.wanandroid.compose.TextTabLayoutComponent
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.config.SilenceStrings

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun ProjectsPage() {

    val selectItem = remember { mutableStateOf(0) }
    val title = ArrayList<String>()
    title.add(SilenceStrings.lastNewProjects)
    title.add(SilenceStrings.projectsType)
    TextTabLayoutComponent(items = title,
        selectedIndex = selectItem.value,
        selectedColor = Color.White,
        indicator = {
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(it[selectItem.value])
                    .padding(
                        start = SilenceSizes.padding10,
                        end = SilenceSizes.padding10,
                        bottom = SilenceSizes.padding2
                    )
                    .height(SilenceSizes.padding2)
                    .background(
                        Color.White,
                        RoundedCornerShape(SilenceSizes.padding5)
                    ),
                color = Color.White
            )
        }, onItemSelected = {
            selectItem.value = it
        })
}