package com.cflower.doitnow.viewmodel

import com.cflower.doitnow.net.AccountService
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.lib_common.viewmodel.BaseViewModel

/**
 * Create By Hosigus at 2020/5/14
 */
class RegisterViewModel : BaseViewModel() {

    fun register(name: String, pwd: String) {
        ApiGenerator.getApiService(AccountService::class.java)
            .register(name, pwd)
            .lifecycleWrapperSubscribeWithProgress {

            }
    }
}