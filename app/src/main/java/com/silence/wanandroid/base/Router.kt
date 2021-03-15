package com.silence.wanandroid.base

import android.app.Activity
import android.content.Context
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

        val instance = Holder.INSTANCE

        fun rout(from: Class<out Activity>, to: Class<out Activity>, bundle: Bundle = Bundle()) {
            val weakReference = instance.routerMap[from.simpleName]
            if (weakReference != null) {
                val get = weakReference.get()
                get?.startActivity(Intent(get, to))
            }
        }

        fun rout(to: Class<out Activity>, bundle: Bundle = Bundle()) {
            val weakReference = instance.stack.peek()
            if (weakReference != null) {
                val get = weakReference.get()
                if (get == null) {
                    instance.stack.pop()
                    rout(to, bundle)
                    return
                }
                val intent = Intent(get, to)
                intent.putExtras(bundle)
                get.startActivity(intent)
            }
        }

        fun back(pageCount: Int = 1) {
            repeat(pageCount) {
                instance.stack.pop()?.get()?.finish()
            }
        }

        fun current(): Activity? {
            return instance.stack.peek()?.get()
        }
    }

    private val stack = Stack<WeakReference<Activity>>()
    private val routerMap = HashMap<String, WeakReference<Activity>>()

    fun attachSelf(className: String, ctx: Activity) {
        val weakReference = WeakReference(ctx)
        routerMap[className] = weakReference
        stack.push(weakReference)
    }

    fun detachSelf(className: String) {
        stack.remove(routerMap[className])
        routerMap.remove(className)
    }

}