package com.cflower.doitnow.net

import com.cflower.doitnow.model.down.Splash
import com.cflower.lib_common.model.ModelWrapper
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Create By Hosigus at 2020/5/14
 */
interface DownService {
    @GET("down/splash")
    fun downSplash(): Observable<ModelWrapper<Splash>>
}