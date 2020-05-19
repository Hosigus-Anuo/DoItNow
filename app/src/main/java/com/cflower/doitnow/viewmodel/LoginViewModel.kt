package com.cflower.doitnow.viewmodel

import com.cflower.doitnow.App
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.doitnow.net.LoginService
import com.cflower.lib_common.viewmodel.BaseViewModel

class LoginViewModel : BaseViewModel() {
    val userModel get() = App.userModel

    fun login(name:String,pwd:String){
        ApiGenerator.getApiService(LoginService::class.java)
            .login(name,pwd)
            .lifecycleWrapperSubscribeWithProgress {
                userModel.bind(it)
            }
    }
}