package com.silence.wanandroid.net

import com.silence.wanandroid.main.home.model.BannerBean
import com.silence.wanandroid.main.home.model.HomeArticleBean
import com.silence.wanandroid.net.model.BaseBean
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
interface WanAndroidApi {

    @GET("banner/json")
    suspend fun getHomeBanner(): BaseBean<BannerBean?>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseBean<HomeArticleBean?>
}