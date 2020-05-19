package com.cflower.doitnow.viewmodel

import com.cflower.doitnow.model.account.User
import com.cflower.doitnow.net.LoginService
import com.cflower.lib_common.model.ModelWrapper
import com.cflower.lib_common.network.ApiGenerator
import com.cflower.lib_common.viewmodel.BaseViewModel

class LoginViewModel : BaseViewModel() {
    fun login(name:String,pwd:String){
        ApiGenerator.getApiService(LoginService::class.java)
            .login(name,pwd)
            .lifecycleWrapperSubscribeWithProgress {

            }
    }
}