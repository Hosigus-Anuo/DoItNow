package com.cflower.doitnow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cflower.doitnow.App
import com.cflower.doitnow.R
import com.cflower.doitnow.model.account.User
import com.cflower.doitnow.net.AccountService
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.model.ModelWrapper
import com.cflower.lib_common.utils.DToast
import com.cflower.lib_common.utils.extensions.isResponseOK
import com.cflower.lib_common.utils.extensions.safeSubscribeBy
import com.cflower.lib_common.viewmodel.BaseViewModel
import com.cflower.lib_common.viewmodel.event.ProgressDialogEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : BaseViewModel() {
    val userModel get() = App.userModel
    val loginSuccess: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun login(name: String, pwd: String) {
        if (name.isEmpty() || pwd.isEmpty()) {
            toastEvent.postValue(R.string.toast_empty_input_login)
            return
        }

        progressDialogEvent.postValue(ProgressDialogEvent.SHOW_NONCANCELABLE_DIALOG_EVENT)
        userModel.login(name, pwd)
        ApiGenerator.getApiService(AccountService::class.java).login()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .doOnError {
                if (it.message?.contains("401") == true) {
                    toastEvent.postValue(R.string.toast_fail_login)
                } else {
                    toastEvent.postValue(R.string.common_default_rx_error)
                }
                progressDialogEvent.postValue(ProgressDialogEvent.DISMISS_DIALOG_EVENT)
            }
            .flatMap {
                if (it.isResponseOK) {
                    val token = it.data
                    if (token == null) {
                        toastEvent.postValue(com.cflower.lib_common.R.string.common_default_server_error)
                    } else {
                        userModel.updateToken(token)
                        return@flatMap ApiGenerator.getApiService(AccountService::class.java)
                            .getUser(name)
                    }
                } else {
                    DToast.asyncShow(BaseApp.context, it.message)
                }
                return@flatMap Observable.empty<ModelWrapper<User>>()
            }.doOnNext {
                if (it.isResponseOK) {
                    it.data?.let { u -> userModel.bind(u) }
                }
            }.observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                progressDialogEvent.postValue(ProgressDialogEvent.DISMISS_DIALOG_EVENT)
            }
            .safeSubscribeBy {
                (loginSuccess as MutableLiveData).postValue(true)
            }
            .lifeCycle()
    }

    fun register(name: String, pwd: String) {
        if (name.isEmpty() || pwd.isEmpty()) {
            toastEvent.postValue(R.string.toast_empty_input_login)
            return
        }

        progressDialogEvent.postValue(ProgressDialogEvent.SHOW_NONCANCELABLE_DIALOG_EVENT)
        ApiGenerator.getApiService(AccountService::class.java).register(userName = name, pwd = pwd)
            .doOnError {
                progressDialogEvent.postValue(ProgressDialogEvent.DISMISS_DIALOG_EVENT)
            }
            .lifecycleWrapperSubscribe {
                toastEvent.postValue(R.string.toast_success_register)
                login(name, pwd)
            }
    }
}