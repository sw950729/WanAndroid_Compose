package com.silence.wanandroid.base

import androidx.appcompat.app.AppCompatActivity
import com.silence.wanandroid.coroutine.SilenceCoroutine


/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
abstract class BaseActivity : AppCompatActivity() {

    val mCoroutine = SilenceCoroutine()

    override fun onDestroy() {
        super.onDestroy()
        mCoroutine.destroy()
    }

}