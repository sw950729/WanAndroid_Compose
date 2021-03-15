package com.silence.wanandroid.utils

import android.content.Context
import android.content.SharedPreferences
import com.silence.wanandroid.MyApplication

/**
 * @author baibai
 * @date 2017/8/19
 */
class SharedPreferencesUtil private constructor() {
    var sp: SharedPreferences

    companion object {

        fun getInstance(): SharedPreferencesUtil {
            return Holder.SP_UTIL
        }

        private object Holder {
            val SP_UTIL = SharedPreferencesUtil()
        }
    }

    /**
     * 保存在手机里面的文件名
     */
    private val spName = "com_silence_wan_android_shared"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    fun setParam(key: String?, value: Any) {
        val editor = sp.edit()
        when (value) {
            is String -> {
                editor.putString(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Long -> {
                editor.putLong(key, value)
            }
        }
        editor.apply()
    }

    fun putString(key: String?, value: String) {
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?, defaultObject: String = ""): String {
        return sp.getString(key, defaultObject) as String
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    fun getParam(key: String?, defaultObject: Any): Any? {
        when (defaultObject.javaClass.simpleName) {
            "String" -> {
                return sp.getString(key, defaultObject as String)
            }
            "Int" -> {
                return sp.getInt(key, defaultObject as Int)
            }
            "Boolean" -> {
                return sp.getBoolean(key, defaultObject as Boolean)
            }
            "Float" -> {
                return sp.getFloat(key, defaultObject as Float)
            }
            "Long" -> {
                return sp.getLong(key, defaultObject as Long)
            }
            else -> return null
        }
    }

    init {
        sp = MyApplication.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE)
    }
}