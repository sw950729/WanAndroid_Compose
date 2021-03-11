package com.silence.wanandroid.net

import android.text.TextUtils
import android.util.Log
import com.silence.wanandroid.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
object RxNetWork {

    private var baseHttpApi: WanAndroidApi? = null

    private var mOkHttpClient: OkHttpClient? = null

    private var baseUrl = "https://www.wanandroid.com"

    private val gsonConverterFactory: Converter.Factory = GsonConverterFactory.create()

    private val rxJavaCallAdapterFactory: CallAdapter.Factory = RxJavaCallAdapterFactory.create()

    private const val defaultTimeOut = 15

    init {
        getOkHttpClient()
    }

    fun getObserverHttps(): WanAndroidApi {
        if (baseHttpApi == null) {
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .build()
            baseHttpApi = retrofit.create(WanAndroidApi::class.java)
        }
        //一定不为空，为了防止外层调用需要判空，这里直接返回不为空的api
        return baseHttpApi!!
    }

    private fun getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized(RxNetWork::class.java) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(getHttpLoggingInterceptor())
                        .addNetworkInterceptor(
                            HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                        .retryOnConnectionFailure(true)
                        .connectTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
                        .readTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
                        .build()
                }
            }
        }
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        //日志显示级别
        val level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor =
            HttpLoggingInterceptor(label@ HttpLoggingInterceptor.Logger { message: String ->
                if (TextUtils.isEmpty(message)) {
                    return@Logger
                }
                if (BuildConfig.DEBUG) {
                    Log.i("RxNetWork", "Retrofit====Message:$message")
                }
            })
        loggingInterceptor.level = level
        return loggingInterceptor
    }
}