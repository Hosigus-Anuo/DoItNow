package com.cflower.doitnow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cflower.doitnow.model.square.Article
import com.cflower.doitnow.net.ApiGenerator
import com.cflower.doitnow.net.LoginService
import com.cflower.lib_common.viewmodel.BaseViewModel

/**
 * Create By Hosigus at 2020/5/22
 */
class SquareViewModel : BaseViewModel() {
    private var page: Int = 0
    val articles: LiveData<List<Article>> = MutableLiveData()
    val loading: LiveData<Boolean> = MutableLiveData()

    fun load() {
        if (loading.value == true) {
            return
        }
        (loading as MutableLiveData).value = true
        //todo 网络请求
    }

    fun refresh() {
        if (loading.value == true) {
            return
        }
        (loading as MutableLiveData).value = true
        page = 0
        load()
    }
}