package com.silence.wanandroid.main.ui

import ViewPager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.compose.TopAppBar
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.config.SilenceStrings
import com.silence.wanandroid.main.home.ui.ArticleListItem
import com.silence.wanandroid.main.home.viewmodel.HomeViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import rememberPagerState

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
    val banner = homeViewModel.bannerData.observeAsState()
    val pagerState = rememberPagerState(maxPage = 3)
    homeViewModel.getHomeArticleList(0)
    homeViewModel.getHomeBanner()
    Column {
        TopAppBar(SilenceStrings.bottomHomeTitle, action = {
            SilenceIcon(
                Icons.Filled.Search,
                modifier = Modifier.padding(end = SilenceSizes.padding4),
                tint = Color.White
            )
        })
        ViewPager(
            state = pagerState,
            maxSize = banner.value?.size,
            maxHeight = (SilenceSizes.padding200)
        ) {
            banner.value?.get(it)?.let { it1 ->
                CoilImage(
                    data = it1.imagePath, null,
                )
            }
        }
        LazyColumn {
            list.value?.datas?.forEach {
                item {
                    ArticleListItem(it)
                }
            }
        }
    }
}