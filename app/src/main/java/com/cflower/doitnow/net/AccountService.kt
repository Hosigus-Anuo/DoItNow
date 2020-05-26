package com.cflower.doitnow.net

import com.cflower.doitnow.model.account.Token
import com.cflower.doitnow.model.account.User
import com.cflower.lib_common.model.ModelWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Create By Hosigus at 2020/5/14
 */
interface AccountService {
    @POST("auth/register")
    @FormUrlEncoded
    fun register(
        @Field("username") userName: String,
        @Field("password") pwd: String
    ): Observable<ModelWrapper<Any>>

    @POST("auth/login")
    @FormUrlEncoded
    fun login(): Observable<ModelWrapper<Token>>

    @GET("uCenter/userInfo")
    @FormUrlEncoded
    fun getUser(
        @Field("username") userName: String
    ): Observable<ModelWrapper<User>>
}