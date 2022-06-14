package com.silence.wanandroid.net

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.silence.wanandroid.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import com.silence.wanandroid.MyApplication

import com.silence.wanandroid.utils.SharedPreferencesUtil
import com.google.gson.reflect.TypeToken
import org.json.JSONArray


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
                        .addInterceptor(getInsertCookieInterceptor())
                        .addInterceptor(getSaveCookieInterceptor())
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

    private fun getSaveCookieInterceptor(): Interceptor {
        //增加cookie信息
        return Interceptor { chain ->
            val request = chain.request()
            val originalResponse = chain.proceed(request)
            if (request.url.toString().contains("user/login")) {
                val headers = originalResponse.headers("Set-Cookie")
                if (headers.isNotEmpty()) {
                    val jsonArray = JsonArray()
                    headers.forEach {
                        jsonArray.add(it)
                    }
                    Log.i("RxNetWork", "jsonArray.toString() = $jsonArray")
                    SharedPreferencesUtil.getInstance().putString(
                        "cookies", jsonArray.toString()
                    )
                }
            }
            originalResponse
        }
    }

    private fun getInsertCookieInterceptor(): Interceptor {
        //缓存
        val cacheFile = File(MyApplication.getApp().cacheDir, "cache")
        //100Mb
        val cache = Cache(cacheFile, 1024 * 1024 * 100)
        //增加头部信息
        return Interceptor { chain ->
            val builder: Request.Builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
            val cookieStr = SharedPreferencesUtil.getInstance().getString("cookies")
            Log.i("RxNetWork", "cookieStr: $cookieStr")

            if (cookieStr.isNotEmpty()) {
                val cookies: JsonArray =
                    MyApplication.getApp().globalGson.fromJson(cookieStr, JsonArray::class.java)
                Log.i("RxNetWork", " cookies.toString() = $cookies")
                cookies.forEach {
                    builder.addHeader("Cookie", it.toString())
                }
            }
            val request: Request = builder.build()
            chain.proceed(request)
        }
    }
}