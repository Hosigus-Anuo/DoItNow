package com.cflower.lib_common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration

/**
 * Create By Hosigus at 2020/5/13
 */
open class BaseApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak", "CI_StaticFieldLeak")
        lateinit var context: Context
            private set

        //当前是不是黑夜模式
        val isNightMode: Boolean
            get() =
                ((context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
                        == Configuration.UI_MODE_NIGHT_YES)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
    }

}