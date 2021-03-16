package com.silence.wanandroid.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.HashMap

class Router private constructor() {

    companion object {
        private object Holder {
            val INSTANCE = Router()
        }

        private val instance = Holder.INSTANCE

        fun attachSelf(className: String, ctx: Activity) {
            instance.attachSelf(className, ctx)
        }

        fun detachSelf(className: String) {
            instance.detachSelf(className)
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

    private val stack = Stack<WeakReference<Activity>>()
    private val routerMap = HashMap<String, WeakReference<Activity>>()

    private fun attachSelf(className: String, ctx: Activity) {
        val weakReference = WeakReference(ctx)
        routerMap[className] = weakReference
        stack.push(weakReference)
    }

    private fun detachSelf(className: String) {
        stack.remove(routerMap[className])
        routerMap.remove(className)
    }

    private fun current(): Activity {
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
        val weakReference = instance.routerMap[from.simpleName]
        weakReference?.get()?.let {
            if (it.isFinishing) {
                startActivity(Companion.current(), to, bundle)
            } else {
                startActivity(it, to, bundle)
            }
        } ?: startActivity(Companion.current(), to, bundle)
    }

    private fun back(
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