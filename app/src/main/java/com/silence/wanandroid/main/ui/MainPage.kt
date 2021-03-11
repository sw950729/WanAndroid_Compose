package com.silence.wanandroid.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silence.wanandroid.R
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceStrings

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Composable
fun MainPage() {
    val currentPage = remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            MyBottomBar(currentPage)
        },
        content = {
            when (currentPage.value) {
                0 -> {
                    HomePage()
                }
                1 -> {
                    NavigationPage()
                }
                2 -> {
                    ProjectsPage()
                }
                3 -> {
                    MinePage()
                }
            }
        }
    )
}


@Composable
fun MyBottomBar(currentPage: MutableState<Int>) {
    BottomAppBar(backgroundColor = Color.White) {
        Row(verticalAlignment = Alignment.Bottom) {
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = 0
                    },
                ImageBitmap.imageResource(id = R.mipmap.switch_home_icon),
                SilenceStrings.bottomHomeTitle,
                if (currentPage.value == 0) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = 1
                    },
                ImageBitmap.imageResource(id = R.mipmap.silence_navigation_icon),
                SilenceStrings.bottomNavigationTitle,
                if (currentPage.value == 1) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = 2
                    },
                ImageBitmap.imageResource(id = R.mipmap.silence_projects_icon),
                SilenceStrings.bottomProjectTitle,
                if (currentPage.value == 2) {
                    SilenceColors.colorMain
                } else {
                    Color.Black
                }
            )
            HomeBottomItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentPage.value = 3
                    },
                ImageBitmap.imageResource(id = R.mipmap.silence_mine_icon),
                SilenceStrings.bottomMineTitle,
                if (currentPage.value == 3) {
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
        modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageBitmap, null,
            Modifier
                .width(25.dp)
                .height(25.dp), tint = color
        )
        Text(title, fontSize = 12.sp, color = color)
    }
}

