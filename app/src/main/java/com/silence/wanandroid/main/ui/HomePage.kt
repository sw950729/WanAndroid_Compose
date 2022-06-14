package com.silence.wanandroid.main.ui

import ViewPager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.compose.SilenceTopAppBar
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.config.SilenceStrings
import com.silence.wanandroid.main.common.toWeb
import com.silence.wanandroid.main.home.ui.ArticleListItem
import com.silence.wanandroid.main.home.viewmodel.HomeViewModel
import rememberPagerState

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@OptIn(ExperimentalPagerApi::class)
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
        SilenceTopAppBar(SilenceStrings.bottomHomeTitle, showBack = false, action = {
            SilenceIcon(
                Icons.Filled.Search,
                modifier = Modifier.padding(end = SilenceSizes.padding4),
                tint = Color.White
            )
        })
        LazyColumn {
            item {
                HorizontalPager(
                    count = banner.value?.size ?: 0,
                    modifier = Modifier.height(SilenceSizes.padding200)
                ) {
                    banner.value?.get(it)?.let { it1 ->
                        Image(
                            painter = rememberImagePainter(
                                data = it1.imagePath,
                                onExecute = ImagePainter.ExecuteCallback.Default
                            ), contentDescription = null, modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    it1.toWeb()
                                }
                        )
                    }
                }
                list.value?.datas?.forEach {
                    ArticleListItem(it, modifier = Modifier.clickable {
                        it.toWeb()
                    })
                }
            }
        }
    }
}