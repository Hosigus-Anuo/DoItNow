package com.cflower.doitnow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cflower.doitnow.App
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.doitnow.model.flag.FlagWrapper
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.doitnow.net.FlagService
import com.cflower.lib_common.viewmodel.BaseViewModel

class FlagViewModel : BaseViewModel() {
    val postFlagStatus: LiveData<Boolean> = MutableLiveData()
    val flags: LiveData<FlagWrapper> = MutableLiveData()

    fun showError() {
        toastEvent.postValue(R.string.toast_show_error)
    }

    fun loadFlagList() {
        ApiGenerator.getApiService(FlagService::class.java)
            .getFlagList(App.userModel.user.userName)
            .lifecycleWrapperSubscribe {
                (flags as MutableLiveData).postValue(it)
            }
    }

    fun postFlag(flag: Flag) {
        ApiGenerator.getApiService(FlagService::class.java)
            .createFlag(
                title = flag.title,
                tag = flag.tag.ordinal,
                type = flag.type.ordinal,
                content = flag.content,
                isPublic = flag.isPublic
            )
            .lifecycleWrapperSubscribeWithProgress {
                (postFlagStatus as MutableLiveData).postValue(true)
            }
    }
}