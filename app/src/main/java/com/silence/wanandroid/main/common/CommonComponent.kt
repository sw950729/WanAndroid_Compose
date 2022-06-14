package com.silence.wanandroid.main.common

import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.main.home.model.ArticleListBean
import com.silence.wanandroid.main.home.model.BannerBean
import com.silence.wanandroid.web.WebActivity
import kotlin.random.Random

fun <T> T.asState(): MutableState<T> {
    return mutableStateOf(this)
}

fun <T> T.asLiveData(): MutableLiveData<T> {
    return MutableLiveData(this)
}

fun Boolean.selectColor(selectColor: Color, unSelectColor: Color): Color {
    return if (this) selectColor else unSelectColor
}

fun CharSequence.toastOnUI() = toastOnUI(this)

fun toastOnUI(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    with(Router.current()) {
        this.runOnUiThread {
            Toast.makeText(this, message, duration).show()
        }
    }
}

fun ArticleListBean.toWeb(){
    val bundle = Bundle()
    bundle.putString(WebActivity.WEB_URL, this.link)
    bundle.putString(WebActivity.WEB_TITLE, this.title)
    bundle.putBoolean(WebActivity.WEB_ICON_SHOW, true)
    bundle.putBoolean(WebActivity.WEB_ICON_COLLECT, this.collect)
    Router.rout(WebActivity::class.java, bundle)
}
fun BannerBean.toWeb(){
    val bundle = Bundle()
    bundle.putString(WebActivity.WEB_URL, this.url)
    bundle.putString(WebActivity.WEB_TITLE, this.title)
    bundle.putBoolean(WebActivity.WEB_ICON_SHOW, true)
    bundle.putBoolean(WebActivity.WEB_ICON_COLLECT, false)
    Router.rout(WebActivity::class.java, bundle)
}


