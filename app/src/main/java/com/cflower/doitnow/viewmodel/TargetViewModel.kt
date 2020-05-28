package com.cflower.doitnow.viewmodel

import com.cflower.lib_common.viewmodel.BaseViewModel

class TargetViewModel : BaseViewModel() {
    var time: Long = 0
    fun saveTime(time: Long) {
        this.time = time
    }

    fun getCurrentTime(): Long {
        return time
    }
}