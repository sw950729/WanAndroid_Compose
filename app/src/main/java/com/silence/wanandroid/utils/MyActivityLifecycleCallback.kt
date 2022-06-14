package com.silence.wanandroid.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.HashMap

class MyActivityLifecycleCallback :Application.ActivityLifecycleCallbacks {

    private val stack = Stack<WeakReference<Activity>>()
    private val routerMap = HashMap<String, WeakReference<Activity>>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        attachSelf(activity.localClassName, activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        detachSelf(activity.localClassName)
    }

    private fun attachSelf(className: String, ctx: Activity) {
        val weakReference = WeakReference(ctx)
        routerMap[className] = weakReference
        stack.push(weakReference)
        Log.i("MyLifecycleCallback","$className 入栈")
    }

    private fun detachSelf(className: String) {
        stack.remove(routerMap[className])
        routerMap.remove(className)
        Log.i("MyLifecycleCallback","$className 出栈")
    }

    fun current(): Activity {
        if (stack.empty()) {
            throw IllegalMonitorStateException("No such active context")
        }
        val weakReference = stack.peek()
        if (weakReference != null) {
            val activity = weakReference.get()
            if (activity != null && !activity.isFinishing) {
                return activity
            }
        }
        stack.pop()
        return current()
    }

    fun getActivity(className: String):Activity{
        val weakReference =  routerMap[className]
     return weakReference?.get()?.let {
         return if (it.isFinishing) {
             current()
         } else {
             it
         }
        } ?: current()
    }


    fun back(
        pageCount: Int = 1,
        includeMainPage: Boolean = true,
        whenIsMainPage: (activity: Activity) -> Unit = {}
    ) {
        repeat(pageCount) {
            if (stack.empty()) {
                return
            }
            if (stack.size == 1) {
                stack.peek().get()?.let { it1 -> whenIsMainPage(it1) }
                if (!includeMainPage) {
                    return
                }
            }
            stack.pop()?.get()?.finish()
        }
    }
}