package com.cflower.doitnow

import com.cflower.doitnow.model.account.UserModel
import com.cflower.lib_common.BaseApp

/**
 * Create By Hosigus at 2020/5/13
 */
class App : BaseApp() {
    companion object {
        lateinit var userModel: UserModel
    }

    override fun onCreate() {
        super.onCreate()
        initLocalData()
    }

    private fun initLocalData() {
        userModel = UserModel()
    }
}