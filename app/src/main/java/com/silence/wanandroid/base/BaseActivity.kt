package com.silence.wanandroid.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.silence.wanandroid.coroutine.SilenceCoroutine
import com.silence.wanandroid.main.ui.MainPage


/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
abstract class BaseActivity : AppCompatActivity() {

    val mCoroutine = SilenceCoroutine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("BaseActivity", "BaseActivity really is " + this.localClassName)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCoroutine.destroy()
    }

}