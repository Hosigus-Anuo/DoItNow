package com.cflower.lib_common.utils.extensions

import android.content.Context
import com.cflower.lib_common.utils.DToast
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Create By Hosigus at 2020/5/13
 */

/**
 * note：请放在有UI操作的操作符前（map等操作符后）, 否则将抛出异常，原因：{@see <a href="https://www.jianshu.com/p/3e5d53e891db"/>}
 */
@CheckReturnValue
fun <T> Observable<T>.setSchedulers(
    subscribeOn: Scheduler = Schedulers.io(),
    unsubscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> = subscribeOn(subscribeOn)
    .unsubscribeOn(unsubscribeOn)
    .observeOn(observeOn)

/**
 * 未实现onError时不会抛出[io.reactivex.exceptions.OnErrorNotImplementedException]异常
 */
fun <T> Observable<T>.safeSubscribeBy(
    onError: (Throwable) -> Unit = {},
    onComplete: () -> Unit = {},
    onNext: (T) -> Unit = {}
): Disposable = subscribe(onNext, onError, onComplete)

