package com.silence.wanandroid.web

import android.os.Bundle
import androidx.activity.compose.setContent
import com.silence.wanandroid.base.BaseActivity

/**
 * @date:2021/3/20
 * @author:Silence
 * @describe:
 **/
class WebActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            intent?.run {
                if (hasExtra(WEB_TITLE) && hasExtra(WEB_URL))
                    WebViewPage(
                        title = extras?.getString(WEB_TITLE) ?: "", url = extras?.getString(
                            WEB_URL
                        ) ?: "",
                        showIcon = extras?.getBoolean(WEB_ICON_SHOW) ?: false,
                        collect = extras?.getBoolean(WEB_ICON_COLLECT) ?: false
                    )
            }
        }
    }

    companion object {
        const val WEB_URL = "webUrl"
        const val WEB_TITLE = "webTitle"
        const val WEB_ICON_SHOW = "webIconShow"
        const val WEB_ICON_COLLECT = "webIconCollect"
    }
}