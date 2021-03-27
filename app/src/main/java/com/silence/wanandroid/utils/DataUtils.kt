package com.silence.wanandroid.utils

/**
 * @date:2021/3/27
 * @author:Silence
 * @describe:
 **/
object DataUtils {
    fun replaceAll(str: String): String {
        var str = str
        str = str.replace("&ldquo;".toRegex(), "\"")
        str = str.replace("&rdquo;".toRegex(), "\"")
        str = str.replace("&quot;".toRegex(), "\"")
        str = str.replace("&mdash;".toRegex(), "\"—")
        str = str.replace("&amp;".toRegex(), "\"&")
        str = str.replace("&middot;".toRegex(), "\"·")
        str = str.replace("&gt;".toRegex(), "\">")
        str = str.replace("&hellip;".toRegex(), "\".\".\".")
        return str
    }
}