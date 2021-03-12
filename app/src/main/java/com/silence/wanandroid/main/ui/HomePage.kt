package com.silence.wanandroid.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.silence.wanandroid.compose.AppBar
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.config.SilenceStrings
import com.silence.wanandroid.main.home.ui.ArticleListItem
import com.silence.wanandroid.main.home.viewmodel.HomeViewModel

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun HomePage() {
    val homeViewModel = HomeViewModel()
    val list = homeViewModel.homeArticleData.observeAsState()
    homeViewModel.getHomeArticleList(0)
    Column {
        AppBar(title = {
            Text(
                text = SilenceStrings.bottomHomeTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
        }, backgroundColor = SilenceColors.colorMain, actions = {
            SilenceIcon(
                Icons.Filled.Search,
                modifier = Modifier.padding(end = SilenceSizes.padding4),
                tint = Color.White
            )
        })
        LazyColumn {
            list.value?.datas?.forEach {
                item {
                    ArticleListItem(it)
                }
            }
        }
    }
}