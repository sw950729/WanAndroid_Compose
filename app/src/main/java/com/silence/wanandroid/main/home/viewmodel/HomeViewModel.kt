package com.silence.wanandroid.main.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.BaseViewModel
import com.silence.wanandroid.main.home.model.ArticleListBean
import com.silence.wanandroid.main.home.model.HomeArticleBean
import com.silence.wanandroid.net.RxNetWork
import kotlinx.coroutines.launch

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
class HomeViewModel : BaseViewModel() {

    var homeArticleData = MutableLiveData<HomeArticleBean>()

    fun getHomeArticleList(page: Int) {
        mCoroutine.launch {
            with(RxNetWork.getObserverHttps().getHomeArticle(page)) {
                homeArticleData.postValue(data)
            }
        }
    }

}