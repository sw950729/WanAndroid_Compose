package com.silence.wanandroid.base

import androidx.lifecycle.ViewModel
import com.silence.wanandroid.coroutine.SilenceCoroutine

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
abstract class BaseViewModel : ViewModel() {

    val mCoroutine = SilenceCoroutine()

    override fun onCleared() {
        super.onCleared()
        mCoroutine.destroy()
    }
}