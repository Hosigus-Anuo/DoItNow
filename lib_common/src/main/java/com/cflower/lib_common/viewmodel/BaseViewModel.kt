package com.cflower.lib_common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.R
import com.cflower.lib_common.model.ModelWrapper
import com.cflower.lib_common.utils.DToast
import com.cflower.lib_common.utils.extensions.isResponseOK
import com.cflower.lib_common.utils.extensions.safeSubscribeBy
import com.cflower.lib_common.utils.extensions.setSchedulers
import com.cflower.lib_common.viewmodel.event.ProgressDialogEvent
import com.cflower.lib_common.viewmodel.event.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Create By Hosigus at 2020/5/13
 */
open class BaseViewModel : ViewModel() {
    val toastEvent: MutableLiveData<Int> by lazy { SingleLiveEvent<Int>() }
    val longToastEvent: MutableLiveData<Int> by lazy { SingleLiveEvent<Int>() }
    val progressDialogEvent: MutableLiveData<ProgressDialogEvent> by lazy { SingleLiveEvent<ProgressDialogEvent>() }

    private val disposables: MutableList<Disposable> = mutableListOf()

    fun Disposable.lifeCycle(): Disposable {
        disposables.add(this)
        return this@lifeCycle
    }

    @CallSuper
    open fun onProgressDialogDismissed() {
        disposeAll()
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposeAll()
    }

    private fun disposeAll() {
        disposables.asSequence()
            .filterNot { it.isDisposed }
            .forEach { it.dispose() }
        disposables.clear()
    }

    protected fun <T> Observable<T>.lifecycleSubscribe(
        onNext: (T) -> Unit
    ) = setSchedulers()
        .defaultErrorHandler()
        .safeSubscribeBy(onNext = onNext)
        .lifeCycle()

    protected inline fun <T> Observable<ModelWrapper<T>>.lifecycleWrapperSubscribe(
        crossinline onNext: (T) -> Unit
    ) = setSchedulers()
        .defaultErrorHandler()
        .safeSubscribeBy {
            if (it.isResponseOK) {
                if (it.data == null) {
                    toastEvent.postValue(R.string.common_default_server_error)
                } else {
                    onNext(it.data)
                }
            } else {
                DToast.asyncShow(BaseApp.context, it.message)
            }
        }
        .lifeCycle()

    protected inline fun <T> Observable<ModelWrapper<T>>.lifecycleWrapperSubscribeWithProgress(
        crossinline onNext: (T) -> Unit
    ){
        progressDialogEvent.postValue(ProgressDialogEvent.SHOW_NONCANCELABLE_DIALOG_EVENT)
        setSchedulers()
            .doOnError {
                toastEvent.postValue(R.string.common_default_rx_error)
                progressDialogEvent.postValue(ProgressDialogEvent.DISMISS_DIALOG_EVENT)
                it.printStackTrace()
            }
            .doOnComplete {
                progressDialogEvent.postValue(ProgressDialogEvent.DISMISS_DIALOG_EVENT)
            }
            .safeSubscribeBy {
                if (it.isResponseOK) {
                    if (it.data == null) {
                        toastEvent.postValue(R.string.common_default_server_error)
                    } else {
                        onNext(it.data)
                    }
                } else {
                    DToast.asyncShow(BaseApp.context, it.message)
                }
            }
            .lifeCycle()
    }

    protected fun <T> Observable<T>.defaultErrorHandler(): Observable<T> = doOnError {
        toastEvent.postValue(R.string.common_default_rx_error)
        it.printStackTrace()
    }
}