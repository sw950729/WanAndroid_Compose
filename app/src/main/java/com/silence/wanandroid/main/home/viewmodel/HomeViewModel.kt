package com.silence.wanandroid.main.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.BaseViewModel
import com.silence.wanandroid.main.home.model.BannerBean
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
    var bannerData = MutableLiveData<MutableList<BannerBean>>()

    fun getHomeArticleList(page: Int) {
        mCoroutine.launch {
            with(RxNetWork.getObserverHttps().getHomeArticle(page)) {
                if (page == 0) {
                    homeArticleData.postValue(null)
                    homeArticleData.postValue(data)
                } else {
                    homeArticleData.postValue(data)
                }
            }
        }
    }

    fun getHomeBanner() {
        mCoroutine.launch {
            with(RxNetWork.getObserverHttps().getHomeBanner()) {
                bannerData.postValue(data)
            }
        }
    }
}