package com.silence.wanandroid.main.ui

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.compose.FlowLayout
import com.silence.wanandroid.compose.TextTabLayoutComponent
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.config.SilenceStrings
import com.silence.wanandroid.main.project.ui.ProjectItem
import com.silence.wanandroid.main.project.viewmodel.ProjectViewModel
import com.silence.wanandroid.utils.DataUtils
import com.silence.wanandroid.web.WebActivity

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun ProjectsPage() {
    val projectViewModel = ProjectViewModel()
    val list = projectViewModel.projectData.observeAsState()
    val projectTree = projectViewModel.projectTree.observeAsState()
    val selectItem = remember { mutableStateOf(0) }
    projectViewModel.getProjectArticleList(0)
    projectViewModel.getProjectTree()
    val title = ArrayList<String>()
    title.add(SilenceStrings.lastNewProjects)
    title.add(SilenceStrings.projectsType)
    Column {
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
        if (selectItem.value == 0) {
            LazyColumn {
                list.value?.datas?.forEach {
                    item {
                        ProjectItem(it, modifier = Modifier.clickable {
                            val bundle = Bundle()
                            bundle.putString(WebActivity.WEB_URL, it.link)
                            bundle.putString(WebActivity.WEB_TITLE, it.title)
                            bundle.putBoolean(WebActivity.WEB_ICON_SHOW, true)
                            bundle.putBoolean(WebActivity.WEB_ICON_COLLECT, it.collect)
                            Router.rout(WebActivity::class.java, bundle)
                        })
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 8.dp)
            ) {
                FlowLayout(
                    datas = projectTree.value,
                ) {
                    Text(
                        text = DataUtils.replaceAll(it.name),
                        color = SilenceColors.random(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 4.dp, top = 10.dp, end = 4.dp, bottom = 10.dp)
                            .background(
                                color = SilenceColors.color_ef,
                                shape = CircleShape
                            )
                            .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

