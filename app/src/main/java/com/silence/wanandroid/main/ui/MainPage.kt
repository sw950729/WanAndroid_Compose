package com.silence.wanandroid.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.silence.wanandroid.R
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.config.MainConfig
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.config.SilenceStrings

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Composable
fun MainPage() {
    val currentPage = remember { mutableStateOf(MainConfig.HOME) }
    Scaffold(
        bottomBar = {
            MyBottomBar(currentPage)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (currentPage.value) {
                MainConfig.HOME -> {
                    HomePage()
                }
                MainConfig.NAVIGATION -> {
                    NavigationPage()
                }
                MainConfig.PROJECT -> {
                    ProjectsPage()
                }
                MainConfig.MINE -> {
                    MinePage()
                }
            }
        }
    }
}


@Composable
fun MyBottomBar(currentPage: MutableState<Int>) {
    BottomAppBar(backgroundColor = Color.White) {
        Row(verticalAlignment = Alignment.Bottom) {
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = MainConfig.HOME
                    },
                ImageBitmap.imageResource(id = R.mipmap.switch_home_icon),
                SilenceStrings.bottomHomeTitle,
                if (currentPage.value == MainConfig.HOME) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = MainConfig.NAVIGATION
                    },
                ImageBitmap.imageResource(id = R.mipmap.silence_navigation_icon),
                SilenceStrings.bottomNavigationTitle,
                if (currentPage.value == MainConfig.NAVIGATION) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = MainConfig.PROJECT
                    },
                ImageBitmap.imageResource(id = R.mipmap.silence_projects_icon),
                SilenceStrings.bottomProjectTitle,
                if (currentPage.value == MainConfig.PROJECT) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = MainConfig.MINE
                    },
                ImageBitmap.imageResource(id = R.mipmap.silence_mine_icon),
                SilenceStrings.bottomMineTitle,
                if (currentPage.value == MainConfig.MINE) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
        }
    }
}


@Composable
fun HomeBottomItem(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    title: String,
    color: Color
) {
    Column(
        modifier.padding(bottom = SilenceSizes.padding8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SilenceIcon(
            imageBitmap,
            Modifier
                .width(SilenceSizes.padding25)
                .height(SilenceSizes.padding25), tint = color
        )
        Text(title, fontSize = SilenceSizes.textSize12, color = color)
    }
}

