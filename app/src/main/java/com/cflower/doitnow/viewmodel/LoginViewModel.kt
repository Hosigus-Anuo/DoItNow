package com.cflower.doitnow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cflower.doitnow.App
import com.cflower.doitnow.R
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.doitnow.net.LoginService
import com.cflower.lib_common.viewmodel.BaseViewModel

class LoginViewModel : BaseViewModel() {
    val userModel get() = App.userModel
    val loginSuccess: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun login(name:String,pwd:String){
        if (name.isEmpty() || pwd.isEmpty()) {
            toastEvent.postValue(R.string.toast_empty_input_login)
            return
        }
        ApiGenerator.getApiService(LoginService::class.java)
            .login(name,pwd)
            .lifecycleWrapperSubscribeWithProgress {
                userModel.bind(it)
                (loginSuccess as MutableLiveData).postValue(true)
            }
    }
}