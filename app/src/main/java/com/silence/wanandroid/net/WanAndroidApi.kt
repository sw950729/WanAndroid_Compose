package com.silence.wanandroid.net

import com.silence.wanandroid.main.home.model.BannerBean
import com.silence.wanandroid.main.home.model.HomeArticleBean
import com.silence.wanandroid.main.mine.model.UserInfo
import com.silence.wanandroid.main.project.model.ChildrenBean
import com.silence.wanandroid.main.project.model.ProjectBean
import com.silence.wanandroid.net.model.BaseBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
interface WanAndroidApi {

    @GET("banner/json")
    suspend fun getHomeBanner(): BaseBean<MutableList<BannerBean>?>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseBean<HomeArticleBean?>

    @POST("user/login")
    suspend fun loginByPassword(
        @Query("username") loginName: String,
        @Query("password") password: String
    ): BaseBean<UserInfo?>

    @POST("user/register")
    suspend fun register(
        @Query("username") loginName: String,
        @Query("password") password: String,
        @Query("repassword") repeatPassword: String,
    ): BaseBean<UserInfo?>

    @GET("/article/listproject/{page}/json")
    suspend fun getProjectArticle(@Path("page") page: Int): BaseBean<ProjectBean?>

    @GET("/project/tree/json")
    suspend fun getProjectTree(): BaseBean<List<ChildrenBean>?>
}