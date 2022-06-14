package com.silence.wanandroid.web

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.compose.SilenceTopAppBar
import com.silence.wanandroid.config.SilenceSizes
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.View
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.main.common.toastOnUI
import java.lang.Exception


/**
 * @date:2021/3/20
 * @author:Silence
 * @describe:
 **/
@Composable
fun WebViewPage(title: String, url: String, showIcon: Boolean, collect: Boolean) {
    Column {
        SilenceTopAppBar(title = title, action = {
            if (showIcon) {
                SilenceIcon(
                    if (collect) {
                        Icons.Outlined.FavoriteBorder
                    } else {
                        Icons.Filled.FavoriteBorder
                    }, modifier =
                    Modifier
                        .padding(end = SilenceSizes.padding4)
                        .size(SilenceSizes.padding25)
                        .clickable {
                            if (collect) {

                            } else {

                            }
                        },
                    tint = if (collect) {
                        Color.Red
                    } else {
                        Color.White
                    }
                )
            }
        })
        WebView(url = url)
    }
}

@Composable
fun WebView(
    url: String,
    enableJavascript: Boolean = true,
    javascriptInterface: Map<String, Any> = mapOf(),
    onPageFinished: ((view: WebView, url: String) -> Unit)? = null,
    onPageStarted: ((view: WebView, url: String) -> Unit)? = null,
    config: (WebView) -> Unit = {},
) {
    var progress by remember { mutableStateOf(0f) }
    Box {
        if (progress != 1f) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .zIndex(1f),
                progress = progress
            )
        }
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).also {
                    it.isFocusable = true
                    it.isFocusableInTouchMode = true
                    it.settings.apply {
                        javaScriptEnabled = enableJavascript
                    }
                    it.webChromeClient = ComposeWebChromeClient(
                        onProgress = {
                            progress = it
                        }
                    )
                    it.webViewClient = ComposeWebViewClient(onPageFinished, onPageStarted)
                    javascriptInterface.forEach { item ->
                        it.addJavascriptInterface(item.value, item.key)
                    }
                    config.invoke(it)
                    it.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                        if (event.action == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && it.canGoBack()) {
                                //表示按返回键时的操作
                                it.goBack()
                                return@OnKeyListener true
                            }
                        }
                        false
                    })
                    it.loadUrl(url)
                }
            },
        )
    }
}

private class ComposeWebViewClient(
    val pageFinished: ((view: WebView, url: String) -> Unit)? = null,
    val pageStarted: ((view: WebView, url: String) -> Unit)? = null,
) : WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        pageFinished?.invoke(view, url)
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        pageStarted?.invoke(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        try {
            if (request?.url?.scheme?.startsWith("http") == false) {
                // 如需支持scheme跳转，可使用以下实现
//                val intent = Intent(Intent.ACTION_VIEW,request.url)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                Router.current().startActivity(intent)
                toastOnUI("禁止跳转第三方App")
                return true
            }
        } catch (e: Exception) {
            return true
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        try {
            if (url?.startsWith("http") == false) {
                // 如需支持scheme跳转，可使用以下实现
//                val intent = Intent(Intent.ACTION_VIEW,Uri.parse(url))
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                Router.current().startActivity(intent)
                toastOnUI("禁止跳转第三方App")
                return true
            }
        } catch (e: Exception) {
            return true
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
}

private class ComposeWebChromeClient(
    private val onProgress: (Float) -> Unit = {}
) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        onProgress.invoke(newProgress.toFloat() / 100f)
    }
}
