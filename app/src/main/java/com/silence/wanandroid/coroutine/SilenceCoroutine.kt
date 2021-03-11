package com.silence.wanandroid.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe: 协程配置
 **/
class SilenceCoroutine : CoroutineScope {

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun destroy() {
        job.cancel()
    }
}