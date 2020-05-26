package com.cflower.doitnow.viewmodel

import com.cflower.doitnow.R
import com.cflower.lib_common.viewmodel.BaseViewModel

class MineViewModel :BaseViewModel(){
    fun showError(){
        toastEvent.postValue(R.string.toast_show_error)
    }
}