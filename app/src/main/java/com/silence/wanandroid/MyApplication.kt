package com.silence.wanandroid

import android.app.Application

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        injectApp(this)
    }

    companion object {
        var mApp: MyApplication? = null

        fun getApp(): MyApplication {
            return mApp?:throw IllegalStateException("You Must To Wait Application Init")
        }

        private fun injectApp(app: MyApplication) {
            mApp = app
        }

    }

}