package com.cflower.doitnow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cflower.doitnow.App
import com.cflower.doitnow.R
import com.cflower.doitnow.net.AccountService
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.doitnow.net.LoginService
import com.cflower.doitnow.net.RegisterService
import com.cflower.lib_common.viewmodel.BaseViewModel

/**
 * Create By Hosigus at 2020/5/14
 */
class RegisterViewModel : BaseViewModel() {
    val userModel get() = App.userModel
    val registerSuccess: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun register(name:String,pwd:String){
        if (name.isEmpty() || pwd.isEmpty()) {
            toastEvent.postValue(R.string.toast_empty_input_login)
            return
        }
        ApiGenerator.getApiService(RegisterService::class.java)
            .register(name,pwd)
            .lifecycleWrapperSubscribeWithProgress {
                userModel.bind(it)
                (registerSuccess as MutableLiveData).postValue(true)
            }
    }
}