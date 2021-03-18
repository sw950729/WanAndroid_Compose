package com.silence.wanandroid

import android.app.Application
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.main.common.asState
import com.silence.wanandroid.main.mine.UserInfo
import com.silence.wanandroid.utils.SharedPreferencesUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private val currentUserInfo = UserInfo().asState()
    lateinit var globalGson: Gson

    override fun onCreate() {
        super.onCreate()
        injectApp(this)
        globalGson = Gson()
        GlobalScope.launch {
            val userInfoStr = SharedPreferencesUtil.getInstance().getString("userInfo")
            if (userInfoStr.isNotEmpty()){
                val userInfo = globalGson.fromJson(userInfoStr, UserInfo::class.java)
                currentUserInfo.value = userInfo
            }
        }
        Router.attachActivityLifecycleCallback()
    }

    companion object {
       private var mApp: MyApplication? = null

        fun getApp(): MyApplication {
            return mApp ?: throw IllegalStateException("You Must To Wait Application Init")
        }

        private fun injectApp(app: MyApplication) {
            mApp = app
        }

    }

    fun userState(): MutableState<UserInfo> {
        return getApp().currentUserInfo
    }

    fun updateUser(user: UserInfo) {
        currentUserInfo.value = user
        SharedPreferencesUtil.getInstance().putString("userInfo", globalGson.toJson(user))
    }

}