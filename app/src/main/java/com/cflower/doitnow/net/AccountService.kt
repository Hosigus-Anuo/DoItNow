package com.cflower.doitnow.net

import com.cflower.lib_common.model.ModelWrapper
import com.cflower.doitnow.model.account.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Create By Hosigus at 2020/5/14
 */
interface AccountService {
    @POST("account/register")
    @FormUrlEncoded
    fun register(
        @Field("name") userName: String,
        @Field("password") pwd: String
    ): Observable<ModelWrapper<User>>
}