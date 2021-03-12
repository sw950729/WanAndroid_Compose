package com.silence.wanandroid.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silence.wanandroid.compose.AppBar
import com.silence.wanandroid.config.SilenceColors
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
    var homeViewModel = HomeViewModel()
    homeViewModel.getHomeArticleList(0)
    Column {
        AppBar(title = {
            Text(
                text = "首页",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
        }, backgroundColor = SilenceColors.colorMain, actions = {
            Icon(
                Icons.Filled.Search,
                null,
                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                tint = Color.White
            )
        })
        LazyColumn {
            item(homeViewModel.homeArticleData.value?.pageCount) {
                homeViewModel.getArticleList()?.let {
                    ArticleListItem(it[0])
                }
            }
        }
    }
}