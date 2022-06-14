package com.silence.wanandroid.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.silence.wanandroid.MyApplication
import com.silence.wanandroid.utils.MyActivityLifecycleCallback
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.HashMap

class Router private constructor() {

    companion object {
        private object Holder {
            val INSTANCE = Router()
        }

        private val instance = Holder.INSTANCE

        fun attachActivityLifecycleCallback() {
            instance.attachActivityLifecycleCallback()
        }

        fun rout(from: Class<out Activity>, to: Class<out Activity>, bundle: Bundle = Bundle()) {
            instance.rout(from, to, bundle)
        }

        fun rout(to: Class<out Activity>, bundle: Bundle = Bundle()) {
            instance.startActivity(current(), to, bundle)
        }

        fun back(
            pageCount: Int = 1,
            includeMainPage: Boolean = true,
            whenIsMainPage: (activity: Activity) -> Unit = {}
        ) {
            instance.back(pageCount, includeMainPage, whenIsMainPage)
        }

        fun backToMain(whenIsMainPage: (activity: Activity) -> Unit = {}) {
            back(Int.MAX_VALUE, false, whenIsMainPage)
        }

        fun current(): Activity {
            return instance.current()
        }
    }

    private var activityStack: MyActivityLifecycleCallback? = null

    fun attachActivityLifecycleCallback() {
        if (activityStack == null) {
            activityStack = MyActivityLifecycleCallback()
            MyApplication.getApp().registerActivityLifecycleCallbacks(activityStack)
        }
    }

    private fun getActivityStack(): MyActivityLifecycleCallback {
        return activityStack ?: let {
            val myActivityLifecycleCallback = MyActivityLifecycleCallback()
            MyApplication.getApp().registerActivityLifecycleCallbacks(myActivityLifecycleCallback)
            myActivityLifecycleCallback
        }
    }

    private fun current(): Activity {
        return getActivityStack().current()
    }

    private fun startActivity(
        from: Activity,
        to: Class<out Activity>,
        bundle: Bundle = Bundle()
    ) {
        val intent = Intent(from, to)
        intent.putExtras(bundle)
        from.startActivity(intent)
    }

    private fun rout(
        from: Class<out Activity>,
        to: Class<out Activity>,
        bundle: Bundle = Bundle()
    ) {
        startActivity(getActivityStack().getActivity(from.simpleName), to, bundle)
    }

    private fun back(
        pageCount: Int = 1,
        includeMainPage: Boolean = true,
        whenIsMainPage: (activity: Activity) -> Unit = {}
    ) {
        getActivityStack().back(pageCount, includeMainPage, whenIsMainPage)
    }

}